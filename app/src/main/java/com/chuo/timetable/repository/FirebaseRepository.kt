package com.chuo.timetable.repository


import androidx.lifecycle.MutableLiveData
import com.chuo.timetable.coroutines.IODispatcher
import com.chuo.timetable.model.Schedule
import com.chuo.timetable.model.Teacher
import com.chuo.timetable.model.TeacherMail
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@ExperimentalCoroutinesApi
class FirebaseRepository
@Inject constructor(@IODispatcher private val dispatcher: CoroutineDispatcher){

    val auth = FirebaseAuth.getInstance()
    val firestore = Firebase.firestore
    val repoLiveData = MutableLiveData<List<Schedule>>()
    val teacherLiveData = MutableLiveData<List<Teacher>>()
    val observeTeacherLiveData = MutableLiveData<List<Teacher>>()
    lateinit var teacherMails: List<TeacherMail>
    fun user(): FirebaseUser? = auth.currentUser

    fun registerTeacher(email: String): Flow<Result<AuthResult>> {
        return flow {
            val result = auth.createUserWithEmailAndPassword(email, "placeHolder").await()
            emit(Result.success(result))
        }.catch {
                emit(Result.failure(it))
            }
            .flowOn(dispatcher)
    }
     fun loginTeacher(email: String, password: String): Flow<Result<AuthResult>>{
         return flow {

             val result = auth.signInWithEmailAndPassword(email, password).await()
             emit(Result.success(result))
         }.catch {
                 emit(Result.failure(it))
             }
             .flowOn(dispatcher)
     }


    fun getTeachers():Flow<Result<List<Teacher>>>{
        return  flow {
            val snapShot = firestore.collection("Teachers")
                .get()
                .await()
            val result = snapShot.documents.map {
                Teacher(
                    it["name"] as String?
                )
            }
            result.let {
                teacherLiveData.postValue(it) }
            emit(Result.success(result))
        }.catch {
                emit(Result.failure(it))
            }
            .flowOn(dispatcher)
    }

    fun isAdmin() : Flow<Result<Boolean>>{
        val currentUser = auth.currentUser?.uid
        return  flow {
            val result = firestore.collection("Timetable")
                .document("Admin")
                .get()
                .await()
                .data
               if(result!!.containsValue(currentUser) ) {
                   emit(Result.success(true))
               }
                else{
                   emit(Result.success(false))
               }
        }.catch {
                emit(Result.failure(it))
            }
            .flowOn(dispatcher)


    }
    fun addTeachersList(teacher: HashMap<String, String>): Flow<Result<DocumentReference>> {
        return flow {
            val result = firestore.collection("Teachers")
                .add(teacher)
                .await()
            emit(Result.success(result))
        }.catch {
                emit(Result.failure(it))
            }
            .flowOn(dispatcher)
    }
    fun addTeachersMail(teacherMail: HashMap<String, String>): Flow<Result<DocumentReference>> {
        return flow {
            val result = firestore.collection("Mails")
                .add(teacherMail)
                .await()
            emit(Result.success(result))
        }.catch {
                emit(Result.failure(it))
            }
            .flowOn(dispatcher)
    }
    fun addAdmin(): Flow<Result<Void>> {
        val userMap = hashMapOf(
            "id" to user()!!.uid
        )
        return flow {
            val result = firestore.collection("Timetable")
                .document("Admin")
                .set(userMap)
                .await()
            emit(Result.success(result))
        }.catch {
                emit(Result.failure(it))
            }
            .flowOn(dispatcher)
    }


   fun observeSchedule(darasa: String,
                       day: String): Flow<Result<List<Schedule>?>> {
      return callbackFlow{
         val documentResult = firestore.collection("Timetable")
              .document(darasa)
              .collection(day)

          val listener = documentResult.addSnapshotListener { messagesSnapshot, exception ->
                  exception?.let {
                      cancel(it.message.toString())
                  }
                  val repo = messagesSnapshot?.documents?.map {
                          Schedule(
                              it["description"] as String?,
                              it["startTime"] as String?,
                              it["endTime"] as String?,
                              it["eventName"] as String?,
                              it["teacherMail"] as String?
                          )

                  }

                  offer(Result.success(repo))

                  repo?.let {
                      repoLiveData.postValue(repo)}

              }
             awaitClose {
              listener.remove()
              cancel()
          }
      }.catch {
              emit(Result.failure(it))
          }
          .flowOn(dispatcher)
   }



    fun observeTutors(): Flow<Result<List<Teacher>?>> {
        return callbackFlow{
            val documentResult = firestore.collection("Teachers")

            val listener = documentResult.addSnapshotListener { messagesSnapshot, exception ->
                exception?.let {
                    cancel(it.message.toString())
                }
                val repo = messagesSnapshot?.documents?.map {
                    Teacher(
                        it["name"] as String?
                    )
                }
                offer(Result.success(repo))

                repo?.let {
                    observeTeacherLiveData.postValue(repo) }

            }
            awaitClose {
                listener.remove()
                cancel()
            }
        }.catch {
                emit(Result.failure(it))
            }
            .flowOn(dispatcher)
    }
    fun observeTutorMail(): Flow<Result<List<TeacherMail>?>> {
        return callbackFlow{
            val documentResult = firestore.collection("Mails")

            val listener = documentResult.addSnapshotListener { messagesSnapshot, exception ->
                exception?.let {
                    cancel(it.message.toString())
                }
                val repo = messagesSnapshot?.documents?.map {
                    TeacherMail(
                        it["name"] as String?,
                        it["email"] as String?
                    )
                }
                offer(Result.success(repo))

                repo?.let { teacherMails = repo }

            }
            awaitClose {
                listener.remove()
                cancel()
            }
        }.catch {
                emit(Result.failure(it))
            }
            .flowOn(dispatcher)
    }

    fun updateSchedule(darasa: String,
                       day: String,
                       item: HashMap<String, String>): Flow<Result<DocumentReference>>{
        return flow {
            val result = firestore.collection("Timetable")
                .document(darasa)
                .collection(day)
                .add(item)
                .await()
            emit(Result.success(result))
        }.catch {
                emit(Result.failure(it))
            }
            .flowOn(dispatcher)

    }
    fun updateCalendar(item: HashMap<String, String>): Flow<Result<DocumentReference>>{
        return flow {
            val result = firestore.collection("Calendar")
                .add(item)
                .await()
            emit(Result.success(result))
        }.catch {
                emit(Result.failure(it))
            }
            .flowOn(dispatcher)

    }
    fun changePassword(email: String): Flow<Result<Task<Void>>> {
        return flow {
           val result = auth.sendPasswordResetEmail(email)
            emit(Result.success(result))
        }.catch {
                emit(Result.failure(it))
            }
            .flowOn(dispatcher)
    }

}
package com.chuo.timetable.testutils

import androidx.lifecycle.MutableLiveData
import com.chuo.timetable.model.Schedule
import com.chuo.timetable.model.Teacher
import com.chuo.timetable.model.TeacherMail
import com.chuo.timetable.repository.Repository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class FakeRepository : Repository{
    override fun auth(): FirebaseAuth? {
        TODO("Not yet implemented")
    }

    override fun user(): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override fun firestore(): FirebaseFirestore {
        TODO("Not yet implemented")
    }

    override fun repoLiveData(): MutableLiveData<List<Schedule>> {
        TODO("Not yet implemented")
    }

    override fun teacherLiveData(): MutableLiveData<List<Teacher>> {
        TODO("Not yet implemented")
    }

    override fun observeTeacherLiveData(): MutableLiveData<List<Teacher>> {
        TODO("Not yet implemented")
    }

    override fun teacherMails(): MutableList<TeacherMail> {
        TODO("Not yet implemented")
    }

    override fun observeSchedule(darasa: String, day: String): Flow<Result<List<Schedule>?>> {
        return flow {
            val schedules = listOf(
                Schedule(
                    "Form One", "8:00",
                    "10:00", "Form One Timetable", "kiprop@gmail.com"
                ),
                Schedule(
                    "Form One", "8:00",
                    "10:00", "Form One Timetable", "kiprop@gmail.com"
                )
            )
            emit(Result.success(schedules))
        }
    }
    override fun isAdmin(): Flow<Result<Boolean>> {
        return flow{
            emit(Result.success(true))
            emit(Result.success(false))
        }
    }

    override fun registerTeacher(email: String): Flow<Result<AuthResult>> {
        TODO("Not yet implemented")
    }

    override fun loginTeacher(email: String, password: String): Flow<Result<AuthResult>> {
        TODO("Not yet implemented")
    }

    override fun getTeachers(): Flow<Result<List<Teacher>>> {
        TODO("Not yet implemented")
    }



    override fun addTeachersList(teacher: HashMap<String, String>): Flow<Result<DocumentReference>> {
        TODO("Not yet implemented")
    }

    override fun addTeachersMail(teacherMail: HashMap<String, String>): Flow<Result<DocumentReference>> {
        TODO("Not yet implemented")
    }

    override fun addAdmin(): Flow<Result<Void>> {
        TODO("Not yet implemented")
    }

    override fun observeTutors(): Flow<Result<List<Teacher>?>> {
        TODO("Not yet implemented")
    }

    override fun observeTutorMail(): Flow<Result<List<TeacherMail>?>> {
        TODO("Not yet implemented")
    }

    override fun updateSchedule(
        darasa: String,
        day: String,
        item: HashMap<String, String>
    ): Flow<Result<DocumentReference>> {
        TODO("Not yet implemented")
    }

    override fun updateCalendar(item: HashMap<String, String>): Flow<Result<DocumentReference>> {
        TODO("Not yet implemented")
    }

    override fun changePassword(email: String): Flow<Result<Task<Void>>> {
        TODO("Not yet implemented")
    }

}
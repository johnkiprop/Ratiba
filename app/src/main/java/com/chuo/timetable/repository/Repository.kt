package com.chuo.timetable.repository

import androidx.lifecycle.MutableLiveData
import com.chuo.timetable.model.Schedule
import com.chuo.timetable.model.Teacher
import com.chuo.timetable.model.TeacherMail
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

interface Repository{
    fun auth(): FirebaseAuth?
    fun user(): FirebaseUser?
    fun firestore(): FirebaseFirestore
    fun repoLiveData():MutableLiveData<List<Schedule>>
    fun teacherLiveData() :MutableLiveData<List<Teacher>>
    fun observeTeacherLiveData(): MutableLiveData<List<Teacher>>
    fun teacherMails():MutableList<TeacherMail>
    fun observeSchedule(darasa: String, day: String): Flow<Result<List<Schedule>?>>
    fun registerTeacher(email: String): Flow<Result<AuthResult>>
    fun loginTeacher(email: String, password: String): Flow<Result<AuthResult>>
    fun getTeachers():Flow<Result<List<Teacher>>>
    fun isAdmin() : Flow<Result<Boolean>>
    fun addTeachersList(teacher: HashMap<String, String>): Flow<Result<DocumentReference>>
    fun addTeachersMail(teacherMail: HashMap<String, String>): Flow<Result<DocumentReference>>
    fun addAdmin(): Flow<Result<Void>>
    fun observeTutors(): Flow<Result<List<Teacher>?>>
    fun observeTutorMail(): Flow<Result<List<TeacherMail>?>>
    fun updateSchedule(darasa: String, day: String, item: HashMap<String, String>): Flow<Result<DocumentReference>>
    fun updateCalendar(item: HashMap<String, String>): Flow<Result<DocumentReference>>
    fun changePassword(email: String): Flow<Result<Task<Void>>>
}
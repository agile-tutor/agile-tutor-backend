package ar.edu.unq.agiletutor.service

import ar.edu.unq.agiletutor.ItemNotFoundException
import ar.edu.unq.agiletutor.UsernameExistException
import ar.edu.unq.agiletutor.model.Course
import ar.edu.unq.agiletutor.model.Student
import ar.edu.unq.agiletutor.model.Tutor
import ar.edu.unq.agiletutor.persistence.CourseRepository
import ar.edu.unq.agiletutor.persistence.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseService {


    @Autowired
    private lateinit var studentService: StudentService

    @Autowired
    private lateinit var repository: CourseRepository

    @Autowired
    private lateinit var studenRepository: StudentRepository

    @Autowired
    private lateinit var senderService: EmailServiceImpl

   // @Autowired
   // private lateinit var attendanceRepository: AttendanceRepository

    @Transactional
    fun register(course: Course): Course {

        if (existSCourse(course)) {
            throw UsernameExistException("Course with name:  ${course.name} is used")
        }

        return repository.save(course)
    }

    private fun existSCourse(course: Course): Boolean {
       /* var bool = false
        val courses = repository.findAll().toMutableList()
        if (courses.isNotEmpty()) {
            bool = courses.any { it.name == course.name }
        }
        return bool
   */
        val courses = repository.findAll().toMutableList()
        return courses.any { it.name == course.name }
    }

    @Transactional
    fun findAll(): List<Course> {
        return repository.findAll()
    }

    @Transactional
    fun findByID(id: Int): Course {
        val course = repository.findById(id)
        if (!(course.isPresent)) {
            throw ItemNotFoundException("Course with Id:  $id not found")
        }
        return course.get()
    }

    @Transactional
    fun tutorFromACourse(id: Int): Tutor {
        val course = findByID(id)
        return course.tutor!!
    }

    @Transactional
    fun studentsFromACourse(id: Int): List<Student> {
        val course = findByID(id)
        return course.students.toMutableList()
    }



    @Transactional
    fun studentsAbsentAtaDay (day: Int): List<Student> {
        return studentService.findAll().filter { ! it.attendedDay(day) }
    }

    @Transactional
    fun studentsAbsentAtaDayFromACourse (id:Int, day: Int): List<Student> {
        return studentsFromACourse(id).filter { ! it.attendedDay(day) }
    }

       @Transactional
       fun markdownAttendance(course:Course, day:Int) {
           val dateclass = course.dateclasses.toMutableList().get(day)
           dateclass.passed = true
           course.dateclasses.toMutableList().set(day, dateclass)
        }

/*
    @Transactional
    fun updateStudentsAttendancesFromACourse(id:Int,  studentAttendance: List<StudentAttendanceDTO>) {
        val course = findByID(id)
        markdownAttendance(course, studentAttendance.first().attendance.day!!)
        studentAttendance.sortedBy { it.studentId }
        for (student in course.students.sortedBy { it.id }) {
            student.updateAttendanceAtADay(studentAttendance.first().attendance.aModelo())
            studentAttendance.iterator().next()
        }
        repository.save(course)
        val students = studentService.studentsNotBlockedAbsentAtAParticularDay(studentAttendance.first().attendance.day!!).toMutableSet()
       senderService.saveAllAbsent(students)

    }
*/
/*
    @Transactional
    fun updateStudentsAttendancesFromACourse(id:Int,  studentAttendances: List<StudentAttendanceDTO>) {
       val course = findByID(id)
        markdownAttendance(course, studentAttendances.first().attendance.day!!)

        for (studentAttendance in studentAttendances) {
           val student =  studentService.findByID(studentAttendance.studentId.toLong())
            student.updateAttendanceAtADay(studentAttendance.attendance.aModelo())

        }
        repository.save(course)
        val students = studentService.studentsNotBlockedAbsentAtAParticularDay(studentAttendances.first().attendance.day!!).toMutableSet()
        senderService.saveAllAbsent(students)

    }
*/

    @Transactional
    fun updateStudentsAttendancesFromACourse(id:Int,  studentAttendances: List<StudentAttendanceDTO>) {
        val course = findByID(id)
        // markdownAttendance(course, studentAttendances.first().attendance.day!!)
        course.markAttendanceAtaDay(studentAttendances.first().attendance.day!!)
        for (studentAttendance in studentAttendances) {
            val student =  studentService.findByID(studentAttendance.studentId.toLong())
            student.updateAttendanceAtADay(studentAttendance.attendance.aModelo())

        }
        repository.save(course)
        val students = studentService.studentsNotBlockedAbsentAtAParticularDay(studentAttendances.first().attendance.day!!).toMutableSet()
        senderService.saveAllAbsent(students)

    }



    @Transactional
    fun update(id: Int, entity: CourseDTO): Course {
       val course = findByID(id)
        course.name= entity.name
        return repository.save(course)
    }


    @Transactional
    fun averageAttendancesFromACourse(id:Int): Double {
        val course = findByID(id)
        if (course.students.isNotEmpty()) {
            return course.students.sumOf { it.attendancePercentage() } / course.students.size
        }
        else
        {return 0.0}
    }

   @Transactional
   fun  addAStudentToACourse(student:Student, id:Int){
       val course = findByID(id)
       student.course = course
       studentService.register(student)
   //course.students.add(student)
      // repository.save(course)

   }

    @Transactional
    fun removeAStudentFromACourse(student:Student, id:Int){
        val course = findByID(id)
        course.students.remove(student)
        repository.save(course)
    }







    //  @Transactional
   // fun updateStudentsAttendancesFromACourse(courseId: Int, studentAttendance: List<StudentAttendanceDTO>) {
        //val course = findByID(id)
     //   studentAttendance.forEach {
       //     attendanceRepository.setAttendanceInfoById(it.attendance.attended.toBoolean(), it.attendance.id)
       // }
     //   senderService.notifyAllAbsent(studentAttendance[0].attendance.day!!, courseId)
       /*     var studentToUpdate = studenRepository.findById(it.studentId.toLong()).get()
            var atendancesUpdated = updateAttendance(
                it.attendance.day!!,
                it.attendance.attended.toBoolean(),
                studentToUpdate.attendances.toMutableList()
            )
            println(studentToUpdate.email + " dia:" + studentAttendance[0].attendance.day + " curso:" + studentToUpdate.course!!.id + "paraupdate")
            studentToUpdate.attendances = atendancesUpdated.toMutableSet()
            for (attendance in studentToUpdate.attendances) {
                println(attendance.attended.toString()+"booleano-dia"+attendance.day)
            }
            studenRepository.save(studentToUpdate)
        }
        senderService.notifyAllAbsent(studentAttendance[0].attendance.day!!, courseId)
        *//*      for (student in course.students) {
                  val boolean = booleans.iterator().next()
                  updateAttendance(day,boolean, student.attendances.toMutableList())
        repository.save(course)*/
  //  }
/*
    private fun updateAttendance(
        day: Int,
        boolean: Boolean,
        attendances: MutableList<Attendance>
    ): MutableList<Attendance> {
        println("$day $boolean aquiestoy")
        val attendance = attendances[day]
        attendance.attended = boolean
        attendances[day] = attendance
        return attendances
    }*/
}
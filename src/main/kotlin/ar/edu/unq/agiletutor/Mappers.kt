package ar.edu.unq.agiletutor

    data class StudentViewMapper(
        val id:Long?,
        val name: String? ,
        val surname: String?,
        val identifier:String?,
        val email:String?,
        val attendances: List<AsistanceViewMapper>,
        val attendancepercentage:Double?,
        val observations:String?
    )

data class AsistanceViewMapper(
    val day: Int?,
    val check:String

)

data class StudentRegisterMapper(
    val name: String? ,
    val surname: String?,
    val identifier:String?,
    val email:String?,
   
)

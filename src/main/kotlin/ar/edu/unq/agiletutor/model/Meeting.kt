package ar.edu.unq.agiletutor.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "meeting")
class Meeting : BaseEntity {

    @Column(nullable = false)
    @NotNull("el day es obligatorio")
    var day: Int? = null

    @JsonFormat(pattern = "yyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(nullable = false, name = "scheduled_day")
    @NotNull("el date es obligatorio")
    var date: String? = null

    @Column(nullable = false)
    @NotNull("el titulo es obligatorio")
    var title: String? = null

    @Column(nullable = false)
    @NotNull("el passed es obligatorio")
    var passed: Boolean = false

    @ManyToOne(optional = true)
    var course: Course? = null

    constructor() : super() {}
    constructor(
            id: Long?,
            day: Int?,
            date: String?,
            title: String,
            passed: Boolean,
            course: Course?
    ) : super() {
        this.id = id
        this.day = day
        this.date = date
        this.title = title
        this.passed = passed
        this.course = course
    }
}
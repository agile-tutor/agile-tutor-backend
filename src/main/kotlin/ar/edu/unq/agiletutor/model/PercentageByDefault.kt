package ar.edu.unq.agiletutor.model

import jakarta.persistence.*
@Entity
@Table(name = "percent")
class PercentageByDefault : BaseEntity {

    @Column (nullable = false)
    var percentageApprovedDefault: Double = 75.00

    constructor() : super() {}
    constructor(
        id: Long,
        percentageApprovedDefault: Double
        ) : super() {
        this.id = id
        this.percentageApprovedDefault = percentageApprovedDefault
     }

    fun getPercentageDefault():Double{
        return percentageApprovedDefault
    }

    fun setPercentageDefault(percentage:Double){
        percentageApprovedDefault = percentage
    }
}
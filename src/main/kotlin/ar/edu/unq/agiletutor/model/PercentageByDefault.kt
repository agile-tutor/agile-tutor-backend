package ar.edu.unq.agiletutor.model

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "percent")
class PercentageByDefault: Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_percentageByDefault")
    var id: Int = 1

    @Column (nullable = false)
    var percentageApprovedDefault: Double = 75.00

    constructor() : super() {}
    constructor(
        id: Int,
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
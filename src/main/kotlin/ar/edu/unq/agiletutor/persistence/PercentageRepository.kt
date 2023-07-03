package ar.edu.unq.agiletutor.persistence


import ar.edu.unq.agiletutor.model.PercentageByDefault
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Configuration
@Repository
interface PercentageRepository  : CrudRepository<PercentageByDefault?, Int?> {

    fun save(percantage: PercentageByDefault): PercentageByDefault
    override fun findById(id: Int): Optional<PercentageByDefault?>
    override fun findAll(): List<PercentageByDefault>
}
package ar.edu.unq.agiletutor.service

import ar.edu.unq.agiletutor.model.PercentageByDefault
import ar.edu.unq.agiletutor.persistence.PercentageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PercentageService {

    @Autowired
    private lateinit var repository: PercentageRepository

    @Transactional
    fun register(percentage: PercentageByDefault): PercentageByDefault {
        return repository.save(percentage)
    }

    @Transactional
    fun percentageByDefault(): Double {
        return repository.findAll().first().percentageApprovedDefault
    }

    @Transactional
    fun updatePercentageByDefault(percentage: Double): PercentageByDefault {
        val percentageByDefault = repository.findAll().first()
        percentageByDefault.setPercentageDefault(percentage)
        return repository.save(percentageByDefault)
    }
}
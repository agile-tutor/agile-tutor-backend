package ar.edu.unq.agiletutor.service

import ar.edu.unq.agilemeeting.service.MeetingRegisterDTO
import ar.edu.unq.agilemeeting.service.MeetingView
import ar.edu.unq.agiletutor.ItemNotFoundException
import ar.edu.unq.agiletutor.UsernameExistException
import ar.edu.unq.agiletutor.model.Meeting
import ar.edu.unq.agiletutor.persistence.MeetingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MeetingService {

    @Autowired
    private lateinit var repository: MeetingRepository

    @Transactional
    fun register(meetingDTO: MeetingRegisterDTO): Meeting {

        if (existSMeeting(meetingDTO)) {
            throw UsernameExistException("Meeting with title:  ${meetingDTO.title} is used")
        }
        println("iddelmeeting" + meetingDTO.id)
        return repository.save(meetingDTO.aModelo())
    }

    @Transactional
    fun findAll(): List<Meeting> {
        return repository.findAll()
    }

    @Transactional
    fun findByID(id: Long): Meeting {
        val meeting = repository.findById(id)
        if (!(meeting.isPresent)) {
            throw ItemNotFoundException("Meeting with Id:  $id not found")
        }
        return meeting.get()
    }

    @Transactional
    fun update(id: Long, meetingDto: MeetingRegisterDTO): MeetingView {
        println(meetingDto.date )
        if (meetingDto.date == null) {
            throw ItemNotFoundException("Meeting date is null")
        }
        val meeting = findByID(id)
        println("modifiedmeeting" + meeting.title)
        meeting.title = meetingDto.title
        meeting.day = meetingDto.day        
        meeting.date = meetingDto.date
        println("modifiedmeeting" + meeting.title)
        return MeetingView.desdeModelo(repository.save(meeting))
    }

    private fun existSMeeting(meeting: MeetingRegisterDTO): Boolean {
        val meetings = repository.findAll().toMutableList()
        return meetings.any { it.title == meeting.title }
    }

    @Transactional
    fun deleteById(id: Long) {
        val meeting = repository.findById(id)
        if (!(meeting.isPresent)) {
            throw ItemNotFoundException("Meeting with Id: $id not found")
        }
        repository.deleteById(id)
    }
}
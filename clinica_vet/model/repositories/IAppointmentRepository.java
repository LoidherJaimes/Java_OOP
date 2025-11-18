package clinica_vet.model.repositories;

import clinica_vet.model.entities.Appointment;
import clinica_vet.model.entities.User;
import clinica_vet.model.entities.Pet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IAppointmentRepository {
    void addAppointment(Appointment appointment);
    void updateAppointment(Appointment appointment);
    void deleteAppointment(UUID id);
    Appointment getAppointmentById(UUID id);
    List<Appointment> getAllAppointments();
    
    List<Appointment> getAppointmentsByDoctor(User doctor);
    List<Appointment> getAppointmentsByPet(Pet pet);
    List<Appointment> getAppointmentsByDate(LocalDate date);
    List<Appointment> getAppointmentsByDoctorAndDate(User doctor, LocalDate date);
    List<Appointment> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end);
    List<Appointment> getAppointmentsByWeek(LocalDate startOfWeek);
}
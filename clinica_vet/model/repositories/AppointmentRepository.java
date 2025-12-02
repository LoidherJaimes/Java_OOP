package clinica_vet.model.repositories;

import clinica_vet.model.entities.Appointment;
import clinica_vet.model.entities.User;
import clinica_vet.model.entities.Pet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AppointmentRepository implements IAppointmentRepository {
    
    private List<Appointment> appointments;

    public AppointmentRepository() {
        this.appointments = new ArrayList<>();
    }

    @Override
    public void addAppointment(Appointment appointment) {
        // If ID is null, generate one
        if (appointment.getId() == null) {
            appointment.setId(UUID.randomUUID());
        }
        appointments.add(appointment);
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getId().equals(appointment.getId())) {
                appointments.set(i, appointment);
                return;
            }
        }
    }

    @Override
    public void deleteAppointment(UUID id) {
        appointments.removeIf(a -> a.getId().equals(id));
    }

    @Override
    public Appointment getAppointmentById(UUID id) {
        return appointments.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(User doctor) {
        if (doctor == null) {
            return new ArrayList<>();
        }
        return appointments.stream()
                .filter(a -> a.getDoctor() != null && 
                           a.getDoctor().getId() == doctor.getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> getAppointmentsByPet(Pet pet) {
        if (pet == null) {
            return new ArrayList<>();
        }
        return appointments.stream()
                .filter(a -> a.getPet() != null && 
                           a.getPet().getId().equals(pet.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        if (date == null) {
            return new ArrayList<>();
        }
        return appointments.stream()
                .filter(a -> a.getDateTime() != null && 
                           a.getDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorAndDate(User doctor, LocalDate date) {
        if (doctor == null || date == null) {
            return new ArrayList<>();
        }
        return appointments.stream()
                .filter(a -> a.getDoctor() != null && 
                           a.getDoctor().getId() == doctor.getId() &&
                           a.getDateTime() != null &&
                           a.getDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return new ArrayList<>();
        }
        return appointments.stream()
                .filter(a -> a.getDateTime() != null &&
                           !a.getDateTime().isBefore(start) && 
                           !a.getDateTime().isAfter(end))
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> getAppointmentsByWeek(LocalDate startOfWeek) {
        if (startOfWeek == null) {
            return new ArrayList<>();
        }
        LocalDateTime weekStart = startOfWeek.atStartOfDay();
        LocalDateTime weekEnd = startOfWeek.plusDays(7).atStartOfDay();
        return getAppointmentsByDateRange(weekStart, weekEnd);
    }

    
}
package clinica_vet.controllers;

import clinica_vet.model.entities.*;
import clinica_vet.model.repositories.*;
import clinica_vet.views.ReportsView;
import clinica_vet.views.MainWindowView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ReportsController {

    private final ReportsView view;
    private final MainWindowView mainWindowView;

    // Ajustado: ahora AppointmentService se usa correctamente
    private final AppointmentService appointmentService;
    private final PetRepository petRepo;
    private final MedicalOrderRepository medicalOrderRepo;
    private final MedicalAttentionRepository medicalAttentionRepo;

    public ReportsController(
            ReportsView view,
            MainWindowView mainWindowView,
            AppointmentService appointmentService,
            PetRepository petRepo,
            MedicalOrderRepository medicalOrderRepository,
            MedicalAttentionRepository medicalAttentionRepository
    ) {
        this.view = view;
        this.mainWindowView = mainWindowView;
        this.appointmentService = appointmentService;
        this.petRepo = petRepo;
        this.medicalOrderRepo = medicalOrderRepository;
        this.medicalAttentionRepo = medicalAttentionRepository;

        setupListeners();
        loadDoctorsIntoCombo();
    }

    private void setupListeners() {
        view.btnRefreshAppointments.addActionListener(e -> generateAppointmentsByDoctorAndStatus());
        view.btnRefreshTop.addActionListener(e -> generateTopReasonsAndSpecies());
        view.btnRefreshIncome.addActionListener(e -> generateIncomeReport());
    }

    private void loadDoctorsIntoCombo() {
        Set<String> doctors = appointmentService.getAllAppointments().stream()
                .map(Appointment::getDoctor)
                .filter(Objects::nonNull)
                .map(User::getUsername)   // getFullName() NO existe, uso username
                .collect(Collectors.toCollection(LinkedHashSet::new));

        JComboBox<String> cb = view.cbDoctors;
        cb.removeAllItems();
        cb.addItem("Todos");
        for (String d : doctors) cb.addItem(d);
    }

    private void generateAppointmentsByDoctorAndStatus() {

        DefaultTableModel mdl = view.mdlAppointments;
        mdl.setRowCount(0);

        String selectedDoctor = (String) view.cbDoctors.getSelectedItem();
        String selectedStatus = (String) view.cbAppointmentStatus.getSelectedItem();

        List<Appointment> all = appointmentService.getAllAppointments();

        List<Appointment> filtered = all.stream()
                .filter(a -> {
                    if (!"Todos".equals(selectedDoctor)) {
                        if (a.getDoctor() == null) return false;
                        if (!a.getDoctor().getUsername().equals(selectedDoctor)) return false;
                    }
                    if (!"Todos".equals(selectedStatus)) {
                        return a.getStatus() != null && a.getStatus().name().equalsIgnoreCase(selectedStatus);
                    }
                    return true;
                })
                .collect(Collectors.toList());

        for (Appointment a : filtered) {
            mdl.addRow(new Object[]{
                    a.getDoctor() != null ? a.getDoctor().getUsername() : "Sin médico",
                    a.getStatus() != null ? a.getStatus().name() : "Sin estado",
                    a.getReason()
            });
        }
    }

    private void generateTopReasonsAndSpecies() {

        DefaultTableModel mdl = view.mdlTopReasons;
        mdl.setRowCount(0);

        List<String> motives = new ArrayList<>();

        appointmentService.getAllAppointments().stream()
                .map(Appointment::getReason)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .forEach(motives::add);

        // TreatmentRepository aún desconocido -> temporal
        // motives.add("Tratamiento X");

        Map<String, Long> motiveCounts =
                motives.stream().collect(Collectors.groupingBy(m -> m, Collectors.counting()));

        Map<String, Long> speciesCounts = petRepo.getAllPets().stream()
                .map(p -> p.getSpecies() != null ? p.getSpecies() : "Desconocida")
                .collect(Collectors.groupingBy(s -> s, LinkedHashMap::new, Collectors.counting()));

        motiveCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .forEach(e -> mdl.addRow(new Object[]{e.getKey(), "Motivo", e.getValue()}));

        mdl.addRow(new Object[]{"---", "---", "---"});

        speciesCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(e -> mdl.addRow(new Object[]{e.getKey(), "Especie", e.getValue()}));
    }

    private void generateIncomeReport() {

    DefaultTableModel mdl = view.mdlIncome;
    mdl.setRowCount(0);

    LocalDate start, end;

    try {
        start = LocalDate.parse(view.tfStartDate.getText().trim());
        end = LocalDate.parse(view.tfEndDate.getText().trim());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(view, "Formato inválido: yyyy-mm-dd");
        return;
    }

    if (end.isBefore(start)) {
        JOptionPane.showMessageDialog(view, "La fecha final no puede ser menor.");
        return;
    }

    LocalDateTime startDt = start.atStartOfDay();
    LocalDateTime endDt = end.plusDays(1).atStartOfDay().minusSeconds(1);

    List<MedicalOrder> orders = medicalOrderRepo.getAllOrders().stream()
            .filter(o -> o.getRequestedDate() != null
                    && !o.getRequestedDate().isBefore(startDt)
                    && !o.getRequestedDate().isAfter(endDt))
            .toList();

    double total = orders.stream()
            .mapToDouble(o -> o.getPrice() != null ? o.getPrice() : 0.0)
            .sum();

    mdl.addRow(new Object[]{
            start + " → " + end,
            orders.size(),
            String.format("%.2f", total)
    });
}

}

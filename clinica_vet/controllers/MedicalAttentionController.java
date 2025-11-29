package clinica_vet.controllers;

import clinica_vet.model.entities.*;
import clinica_vet.model.repositories.*;
import clinica_vet.views.AddMedicalOrderView;
import clinica_vet.views.AddTreatmentView;
import clinica_vet.views.MainWindowView;
import clinica_vet.views.MedicalAttentionView;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * Controlador para la vista de atención médica.
 * Maneja el registro de síntomas, diagnóstico, tratamientos y órdenes médicas.
 */
public class MedicalAttentionController {
    
    private MedicalAttentionView view;
    private MainWindowView mainWindow;
    private Appointment appointment;
    private User currentVeterinarian;
    
    // Repositorios
    private MedicalAttentionRepository attentionRepository;
    private TreatmentRepository treatmentRepository;
    private MedicalOrderRepository orderRepository;
    private AppointmentService appointmentService;
    
    // Atención actual
    private MedicalAttention currentAttention;
    private List<Treatment> currentTreatments;
    private List<MedicalOrder> currentOrders;
    
    // Referencias para navegación
    private Runnable onReturnCallback;
    
    /**
     * Constructor del controlador.
     * @param view Vista de atención médica
     * @param mainWindow Ventana principal para navegación
     * @param appointment Cita a atender
     * @param currentVeterinarian Usuario veterinario que atiende
     * @param attentionRepository Repositorio de atenciones
     * @param treatmentRepository Repositorio de tratamientos
     * @param orderRepository Repositorio de órdenes
     * @param appointmentService Servicio de citas
     */
    public MedicalAttentionController(
            MedicalAttentionView view,
            MainWindowView mainWindow,
            Appointment appointment,
            User currentVeterinarian,
            MedicalAttentionRepository attentionRepository,
            TreatmentRepository treatmentRepository,
            MedicalOrderRepository orderRepository,
            AppointmentService appointmentService) {
        
        this.view = view;
        this.mainWindow = mainWindow;
        this.appointment = appointment;
        this.currentVeterinarian = currentVeterinarian;
        this.attentionRepository = attentionRepository;
        this.treatmentRepository = treatmentRepository;
        this.orderRepository = orderRepository;
        this.appointmentService = appointmentService;
        
        this.currentTreatments = new ArrayList<>();
        this.currentOrders = new ArrayList<>();
        
        initializeController();
        loadOrCreateAttention();
    }
    
    public void setOnReturnCallback(Runnable callback) {
        this.onReturnCallback = callback;
    }
    
    private void initializeController() {
        view.getBtnAddTreatment().addActionListener(e -> handleAddTreatment());
        view.getBtnEditTreatment().addActionListener(e -> handleEditTreatment());
        view.getBtnDeleteTreatment().addActionListener(e -> handleDeleteTreatment());
        
        view.getBtnAddOrder().addActionListener(e -> handleAddOrder());
        view.getBtnEditOrder().addActionListener(e -> handleEditOrder());
        view.getBtnDeleteOrder().addActionListener(e -> handleDeleteOrder());
        
        view.getBtnSave().addActionListener(e -> handleSaveEvolution());
        view.getBtnCloseAttention().addActionListener(e -> handleCloseAttention());
        view.getBtnCancel().addActionListener(e -> handleCancel());
    }
    
    private void loadOrCreateAttention() {
        if (appointment.getMedicalAttentionId() != null) {
            currentAttention = attentionRepository.getAttentionById(appointment.getMedicalAttentionId());
            if (currentAttention != null) {
                loadExistingAttention();
                return;
            }
        }
        
        currentAttention = attentionRepository.getAttentionByAppointmentId(appointment.getId());
        if (currentAttention != null) {
            loadExistingAttention();
            return;
        }
        
        createNewAttention();
    }
    
    private void createNewAttention() {
        currentAttention = new MedicalAttention(
            appointment.getId(),
            appointment.getPet().getId(),
            currentVeterinarian.getId()
        );
        
        loadAttentionInfo();
    }
    
    private void loadExistingAttention() {
        view.setSymptoms(currentAttention.getSymptoms());
        view.setDiagnosis(currentAttention.getDiagnosis());
        view.setProcedures(currentAttention.getProcedures());
        
        currentTreatments = treatmentRepository.getTreatmentsByAttentionId(currentAttention.getId());
        refreshTreatmentsTable();
        
        currentOrders = orderRepository.getOrdersByAttentionId(currentAttention.getId());
        refreshOrdersTable();
        
        loadAttentionInfo();
        
        if (currentAttention.isClosed()) {
            disableEditing();
        }
    }
    
    private void loadAttentionInfo() {
        Pet pet = appointment.getPet();
        Owner owner = pet.getOwner();
        
        String petInfo = String.format("%s - %s (%s)", 
            pet.getName(), 
            pet.getSpecies(), 
            pet.getRace());
        
        String ownerInfo = String.format("%s - Tel: %s", 
            owner.getName(), 
            owner.getPhone());
        
        String dateInfo = appointment.getFormattedDateTime();
        
        String vetInfo = currentVeterinarian.getUsername();
        
        view.setPetInfo(petInfo);
        view.setOwnerInfo(ownerInfo);
        view.setDateInfo(dateInfo);
        view.setVeterinarianInfo(vetInfo);
    }
    
    private void disableEditing() {
        view.getBtnSave().setEnabled(false);
        view.getBtnCloseAttention().setEnabled(false);
        view.getBtnAddTreatment().setEnabled(false);
        view.getBtnEditTreatment().setEnabled(false);
        view.getBtnDeleteTreatment().setEnabled(false);
        view.getBtnAddOrder().setEnabled(false);
        view.getBtnEditOrder().setEnabled(false);
        view.getBtnDeleteOrder().setEnabled(false);
        
        JOptionPane.showMessageDialog(view,
            "Esta atención ya fue cerrada y no puede ser modificada.\nModo: Solo lectura",
            "Atención Cerrada",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleAddTreatment() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
        AddTreatmentView treatmentView = new AddTreatmentView(parentFrame);
        
        treatmentView.getBtnSave().addActionListener(e -> {
            if (treatmentView.validateFields()) {
                Treatment treatment = new Treatment(
                    null,
                    treatmentView.getMedication(),
                    treatmentView.getDosage(),
                    treatmentView.getFrequency(),
                    treatmentView.getDuration(),
                    treatmentView.getInstructions()
                );
                
                currentTreatments.add(treatment);
                refreshTreatmentsTable();
                
                treatmentView.setConfirmed(true);
                treatmentView.dispose();
                
                JOptionPane.showMessageDialog(view,
                    "Tratamiento agregado. Recuerde guardar la evolución.",
                    "Tratamiento Agregado",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        treatmentView.setVisible(true);
    }
    
    private void handleEditTreatment() {
        int selectedRow = view.getSelectedTreatmentRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view,
                "Por favor, seleccione un tratamiento para editar.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Treatment treatment = currentTreatments.get(selectedRow);
        
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
        AddTreatmentView treatmentView = new AddTreatmentView(parentFrame, "Editar Tratamiento");
        
        // Pre-cargar datos
        treatmentView.setMedication(treatment.getMedication());
        treatmentView.setDosage(treatment.getDosage());
        treatmentView.setFrequency(treatment.getFrequency());
        treatmentView.setDuration(treatment.getDuration());
        treatmentView.setInstructions(treatment.getInstructions());
        
        treatmentView.getBtnSave().addActionListener(e -> {
            if (treatmentView.validateFields()) {
                treatment.setMedication(treatmentView.getMedication());
                treatment.setDosage(treatmentView.getDosage());
                treatment.setFrequency(treatmentView.getFrequency());
                treatment.setDuration(treatmentView.getDuration());
                treatment.setInstructions(treatmentView.getInstructions());
                
                refreshTreatmentsTable();
                
                treatmentView.setConfirmed(true);
                treatmentView.dispose();
                
                JOptionPane.showMessageDialog(view,
                    "Tratamiento actualizado. Recuerde guardar la evolución.",
                    "Tratamiento Actualizado",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        treatmentView.setVisible(true);
    }
    
    private void handleDeleteTreatment() {
        int selectedRow = view.getSelectedTreatmentRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view,
                "Por favor, seleccione un tratamiento para eliminar.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view,
            "¿Está seguro de eliminar este tratamiento?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            Treatment treatment = currentTreatments.get(selectedRow);
            
            // Si tiene ID, eliminarlo del repositorio
            if (treatment.getId() != null) {
                treatmentRepository.deleteTreatment(treatment.getId());
            }
            
            currentTreatments.remove(selectedRow);
            refreshTreatmentsTable();
            
            JOptionPane.showMessageDialog(view,
                "Tratamiento eliminado.",
                "Eliminado",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void refreshTreatmentsTable() {
        view.clearTreatmentsTable();
        for (Treatment treatment : currentTreatments) {
            view.addTreatmentToTable(
                treatment.getId() != null ? treatment.getId().toString() : "",
                treatment.getMedication(),
                treatment.getDosage(),
                treatment.getFrequency(),
                treatment.getDuration()
            );
        }
    }
    
    // ========== GESTIÓN DE ÓRDENES MÉDICAS ==========
    
    private void handleAddOrder() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
        AddMedicalOrderView orderView = new AddMedicalOrderView(parentFrame);
        
        orderView.getBtnSave().addActionListener(e -> {
            if (orderView.validateFields()) {
                MedicalOrder order = new MedicalOrder(
                    null, // Se asignará cuando se guarde la atención
                    orderView.getSelectedOrderType(),
                    orderView.getDescription(),
                    orderView.getNotes()
                );
                
                currentOrders.add(order);
                refreshOrdersTable();
                
                orderView.setConfirmed(true);
                orderView.dispose();
                
                JOptionPane.showMessageDialog(view,
                    "Orden médica agregada. Recuerde guardar la evolución.",
                    "Orden Agregada",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        orderView.setVisible(true);
    }
    
    private void handleEditOrder() {
        int selectedRow = view.getSelectedOrderRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view,
                "Por favor, seleccione una orden para editar.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        MedicalOrder order = currentOrders.get(selectedRow);
        
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
        AddMedicalOrderView orderView = new AddMedicalOrderView(parentFrame, "Editar Orden Médica");
        
        // Pre-cargar datos
        orderView.setSelectedOrderType(order.getOrderType());
        orderView.setDescription(order.getDescription());
        orderView.setNotes(order.getNotes());
        
        orderView.getBtnSave().addActionListener(e -> {
            if (orderView.validateFields()) {
                order.setOrderType(orderView.getSelectedOrderType());
                order.setDescription(orderView.getDescription());
                order.setNotes(orderView.getNotes());
                
                refreshOrdersTable();
                
                orderView.setConfirmed(true);
                orderView.dispose();
                
                JOptionPane.showMessageDialog(view,
                    "Orden actualizada. Recuerde guardar la evolución.",
                    "Orden Actualizada",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        orderView.setVisible(true);
    }
    
    private void handleDeleteOrder() {
        int selectedRow = view.getSelectedOrderRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view,
                "Por favor, seleccione una orden para eliminar.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view,
            "¿Está seguro de eliminar esta orden médica?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            MedicalOrder order = currentOrders.get(selectedRow);
            
            // Si tiene ID, eliminarlo del repositorio
            if (order.getId() != null) {
                orderRepository.deleteOrder(order.getId());
            }
            
            currentOrders.remove(selectedRow);
            refreshOrdersTable();
            
            JOptionPane.showMessageDialog(view,
                "Orden eliminada.",
                "Eliminada",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void refreshOrdersTable() {
        view.clearOrdersTable();
        for (MedicalOrder order : currentOrders) {
            view.addOrderToTable(
                order.getId() != null ? order.getId().toString() : "",
                order.getOrderType().getDisplayName(),
                order.getDescription(),
                order.getFormattedDate()
            );
        }
    }
    
    // ========== GUARDAR Y CERRAR ==========
    
    private void handleSaveEvolution() {
        if (!validateFields()) {
            return;
        }
        
        // Actualizar datos de la atención
        currentAttention.setSymptoms(view.getSymptoms());
        currentAttention.setDiagnosis(view.getDiagnosis());
        currentAttention.setProcedures(view.getProcedures());
        
        // Guardar o actualizar atención
        if (attentionRepository.getAttentionById(currentAttention.getId()) == null) {
            attentionRepository.addAttention(currentAttention);
            
            // Vincular con la cita
            appointment.setMedicalAttentionId(currentAttention.getId());
            appointmentService.updateAppointment(appointment);
        } else {
            attentionRepository.updateAttention(currentAttention);
        }
        
        // Guardar tratamientos
        saveTreatments();
        
        // Guardar órdenes
        saveOrders();
        
        JOptionPane.showMessageDialog(view,
            "Evolución médica guardada exitosamente.",
            "Guardado Exitoso",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void saveTreatments() {
        for (Treatment treatment : currentTreatments) {
            treatment.setMedicalAttentionId(currentAttention.getId());
            
            if (treatment.getId() == null) {
                treatmentRepository.addTreatment(treatment);
            } else {
                treatmentRepository.updateTreatment(treatment);
            }
        }
    }
    
    private void saveOrders() {
        for (MedicalOrder order : currentOrders) {
            order.setMedicalAttentionId(currentAttention.getId());
            
            if (order.getId() == null) {
                orderRepository.addOrder(order);
            } else {
                orderRepository.updateOrder(order);
            }
        }
    }
    
    private void handleCloseAttention() {
        if (!validateFields()) {
            return;
        }
        
        // Mostrar diálogo para seleccionar estado de cierre
        String[] options = {"Atendida", "No Asistió", "Reprogramar"};
        int choice = JOptionPane.showOptionDialog(view,
            "Seleccione el estado de cierre de la cita:",
            "Cerrar Cita",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        if (choice < 0) {
            return; // Usuario canceló
        }
        
        // Mapear selección a AttentionStatus
        AttentionStatus status;
        AppointmentStatus appointmentStatus;
        
        switch (choice) {
            case 0: // Atendida
                status = AttentionStatus.COMPLETED;
                appointmentStatus = AppointmentStatus.COMPLETED;
                break;
            case 1: // No Asistió
                status = AttentionStatus.NO_SHOW;
                appointmentStatus = AppointmentStatus.CANCELLED;
                break;
            case 2: // Reprogramar
                status = AttentionStatus.RESCHEDULED;
                appointmentStatus = AppointmentStatus.PENDING;
                break;
            default:
                return;
        }
        
        // Solicitar notas de cierre
        String notes = JOptionPane.showInputDialog(view,
            "Notas de cierre (opcional):",
            "Notas de Cierre",
            JOptionPane.PLAIN_MESSAGE);
        
        // Actualizar atención
        currentAttention.setSymptoms(view.getSymptoms());
        currentAttention.setDiagnosis(view.getDiagnosis());
        currentAttention.setProcedures(view.getProcedures());
        currentAttention.close(status, notes != null ? notes : "");
        
        // Guardar todo
        if (attentionRepository.getAttentionById(currentAttention.getId()) == null) {
            attentionRepository.addAttention(currentAttention);
            appointment.setMedicalAttentionId(currentAttention.getId());
        } else {
            attentionRepository.updateAttention(currentAttention);
        }
        
        saveTreatments();
        saveOrders();
        
        // Actualizar estado de la cita
        appointment.setStatus(appointmentStatus);
        appointmentService.updateAppointment(appointment);
        
        JOptionPane.showMessageDialog(view,
            "Cita cerrada exitosamente con estado: " + status.getDisplayName(),
            "Cita Cerrada",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Volver a la vista anterior
        handleCancel();
    }
    
    private boolean validateFields() {
        if (view.getSymptoms().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Debe ingresar los síntomas del paciente.",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (view.getDiagnosis().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Debe ingresar el diagnóstico.",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void handleCancel() {
        int confirm = JOptionPane.showConfirmDialog(view,
            "¿Está seguro de volver? Los cambios no guardados se perderán.",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (onReturnCallback != null) {
                onReturnCallback.run();
            } else {
                mainWindow.setContent(mainWindow.getWelcomeView());
            }
        }
    }
}
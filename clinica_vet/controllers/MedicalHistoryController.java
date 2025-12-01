package clinica_vet.controllers;

import clinica_vet.model.entities.*;
import clinica_vet.model.repositories.*;
import clinica_vet.views.MainWindowView;
import clinica_vet.views.MedicalHistoryView;

import javax.swing.*;
import java.util.List;
import java.util.UUID;

public class MedicalHistoryController {
    
    private MedicalHistoryView view;
    private MainWindowView mainWindow;
    
    // Repositorios
    private MedicalAttentionRepository attentionRepository;
    private TreatmentRepository treatmentRepository;
    private MedicalOrderRepository orderRepository;
    private PetRepository petRepository;
    private UserRepository userRepository;
    
    private Pet currentPet;
    
    public MedicalHistoryController(
            MedicalHistoryView view,
            MainWindowView mainWindow,
            MedicalAttentionRepository attentionRepository,
            TreatmentRepository treatmentRepository,
            MedicalOrderRepository orderRepository,
            PetRepository petRepository,
            UserRepository userRepository) {
        
        this.view = view;
        this.mainWindow = mainWindow;
        this.attentionRepository = attentionRepository;
        this.treatmentRepository = treatmentRepository;
        this.orderRepository = orderRepository;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        
        initializeController();
    }
    
    private void initializeController() {
        view.getBtnSearch().addActionListener(e -> handleSearch());
        view.getTxtSearchPet().addActionListener(e -> handleSearch());
        view.getBtnViewDetail().addActionListener(e -> handleViewDetail());
        view.getBtnPrint().addActionListener(e -> handlePrint());
        view.getBtnExport().addActionListener(e -> handleExport());
        view.getBtnClose().addActionListener(e -> handleClose());
        
        view.getTableAttentions().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleAttentionSelection();
            }
        });
    }
    
    private void handleSearch() {
        String searchText = view.getSearchText();
        
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Por favor, ingrese el nombre de la mascota a buscar.",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        List<Pet> allPets = petRepository.getAllPets();
        List<Pet> matchingPets = allPets.stream()
            .filter(pet -> pet.getName().toLowerCase().contains(searchText.toLowerCase()))
            .toList();
        
        if (matchingPets.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "No se encontraron mascotas con ese nombre.",
                "Sin Resultados",
                JOptionPane.INFORMATION_MESSAGE);
            view.clearPetInfo();
            view.clearAttentionsTable();
            view.clearAttentionDetail();
            currentPet = null;
            return;
        }
        
        Pet selectedPet;
        if (matchingPets.size() > 1) {
            selectedPet = showPetSelectionDialog(matchingPets);
            if (selectedPet == null) {
                return;
            }
        } else {
            selectedPet = matchingPets.get(0);
        }
        
        loadPetInformation(selectedPet);
    }
    
    private Pet showPetSelectionDialog(List<Pet> pets) {
        String[] petNames = pets.stream()
            .map(pet -> String.format("%s - %s (%s)", 
                pet.getName(), 
                pet.getSpecies(), 
                pet.getOwner() != null ? pet.getOwner().getName() : "Sin dueño"))
            .toArray(String[]::new);
        
        String selected = (String) JOptionPane.showInputDialog(
            view,
            "Se encontraron múltiples mascotas. Seleccione una:",
            "Seleccionar Mascota",
            JOptionPane.QUESTION_MESSAGE,
            null,
            petNames,
            petNames[0]
        );
        
        if (selected == null) {
            return null;
        }
        
        int index = java.util.Arrays.asList(petNames).indexOf(selected);
        return pets.get(index);
    }
    
    private void loadPetInformation(Pet pet) {
        currentPet = pet;
        
        // Mostrar información de la mascota
        String petInfo = String.format("%s - %s, %s", 
            pet.getName(), 
            pet.getSpecies(), 
            pet.getRace());
        
        String ownerInfo = pet.getOwner() != null 
            ? String.format("%s - Tel: %s", pet.getOwner().getName(), pet.getOwner().getPhone())
            : "Sin propietario registrado";
        
        String petDetails = String.format("Edad: %.1f años, Peso: %.1f kg, Sexo: %s", 
            pet.getAge(), 
            pet.getWeight(), 
            pet.getSex() != null ? pet.getSex().toString() : "No especificado");
        
        view.setPetInfo(petInfo);
        view.setOwnerInfo(ownerInfo);
        view.setPetDetails(petDetails);
        
        // Cargar historial de atenciones
        loadMedicalHistory(pet.getId());
    }
    
    private void loadMedicalHistory(UUID petId) {
        view.clearAttentionsTable();
        view.clearAttentionDetail();
        
        List<MedicalAttention> attentions = attentionRepository.getAttentionsByPetId(petId);
        
        if (attentions.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Esta mascota no tiene atenciones médicas registradas.",
                "Sin Historial",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        for (MedicalAttention attention : attentions) {
            User veterinarian = userRepository.getUserById(attention.getVeterinarianId());
            String vetName = veterinarian != null ? veterinarian.getUsername() : "Desconocido";
            
            String diagnosis = attention.getDiagnosis();
            if (diagnosis.length() > 50) {
                diagnosis = diagnosis.substring(0, 47) + "...";
            }
            
            view.addAttentionToTable(
                attention.getId().toString(),
                attention.getFormattedDate(),
                vetName,
                diagnosis,
                attention.getStatus().getDisplayName()
            );
        }
    }
    
    private void handleAttentionSelection() {
        String selectedId = view.getSelectedAttentionId();
        if (selectedId == null) {
            view.clearAttentionDetail();
            return;
        }
        
        try {
            UUID attentionId = UUID.fromString(selectedId);
            MedicalAttention attention = attentionRepository.getAttentionById(attentionId);
            
            if (attention != null) {
                displayAttentionDetail(attention);
            }
        } catch (Exception ex) {
            view.clearAttentionDetail();
        }
    }
    
    private void displayAttentionDetail(MedicalAttention attention) {
        StringBuilder detail = new StringBuilder();
        
        User veterinarian = userRepository.getUserById(attention.getVeterinarianId());
        String vetName = veterinarian != null ? veterinarian.getUsername() : "Desconocido";
        
        detail.append("══════════════════════════════════════════════\n");
        detail.append("ATENCIÓN MÉDICA\n");
        detail.append("══════════════════════════════════════════════\n\n");
        
        detail.append("Fecha: ").append(attention.getFormattedDateTime()).append("\n");
        detail.append("Veterinario: ").append(vetName).append("\n");
        detail.append("Estado: ").append(attention.getStatus().getDisplayName()).append("\n\n");
        
        // Evolución médica
        detail.append("─────────────────────────────────────────────\n");
        detail.append("SÍNTOMAS:\n");
        detail.append(attention.getSymptoms()).append("\n\n");
        
        detail.append("DIAGNÓSTICO:\n");
        detail.append(attention.getDiagnosis()).append("\n\n");
        
        if (attention.getProcedures() != null && !attention.getProcedures().isEmpty()) {
            detail.append("PROCEDIMIENTOS REALIZADOS:\n");
            detail.append(attention.getProcedures()).append("\n\n");
        }
        
        List<Treatment> treatments = treatmentRepository.getTreatmentsByAttentionId(attention.getId());
        if (!treatments.isEmpty()) {
            detail.append("─────────────────────────────────────────────\n");
            detail.append("TRATAMIENTOS PRESCRITOS:\n\n");
            int i = 1;
            for (Treatment treatment : treatments) {
                detail.append(i++).append(". ").append(treatment.getMedication()).append("\n");
                detail.append("   Dosis: ").append(treatment.getDosage()).append("\n");
                detail.append("   Frecuencia: ").append(treatment.getFrequency()).append("\n");
                detail.append("   Duración: ").append(treatment.getDuration()).append("\n");
                if (treatment.getInstructions() != null && !treatment.getInstructions().isEmpty()) {
                    detail.append("   Instrucciones: ").append(treatment.getInstructions()).append("\n");
                }
                detail.append("\n");
            }
        }
        
        List<MedicalOrder> orders = orderRepository.getOrdersByAttentionId(attention.getId());
        if (!orders.isEmpty()) {
            detail.append("─────────────────────────────────────────────\n");
            detail.append("ÓRDENES MÉDICAS:\n\n");
            int i = 1;
            for (MedicalOrder order : orders) {
                detail.append(i++).append(". ").append(order.getOrderType().getDisplayName()).append("\n");
                detail.append("   Descripción: ").append(order.getDescription()).append("\n");
                detail.append("   Estado: ").append(order.getStatusText()).append("\n");
                if (order.getNotes() != null && !order.getNotes().isEmpty()) {
                    detail.append("   Notas: ").append(order.getNotes()).append("\n");
                }
                detail.append("\n");
            }
        }
        
        if (attention.isClosed() && attention.getClosureNotes() != null && 
            !attention.getClosureNotes().isEmpty()) {
            detail.append("─────────────────────────────────────────────\n");
            detail.append("NOTAS DE CIERRE:\n");
            detail.append(attention.getClosureNotes()).append("\n");
        }
        
        detail.append("══════════════════════════════════════════════\n");
        
        view.setAttentionDetail(detail.toString());
    }
    
    private void handleViewDetail() {
        String selectedId = view.getSelectedAttentionId();
        if (selectedId == null) {
            JOptionPane.showMessageDialog(view,
                "Por favor, seleccione una atención para ver el detalle.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            UUID attentionId = UUID.fromString(selectedId);
            MedicalAttention attention = attentionRepository.getAttentionById(attentionId);
            
            if (attention != null) {
                // Crear ventana de detalle
                JDialog detailDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(view), 
                    "Detalle de Atención Médica", true);
                detailDialog.setSize(600, 500);
                detailDialog.setLocationRelativeTo(view);
                
                JTextArea textArea = new JTextArea();
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                
                StringBuilder detail = new StringBuilder();
                displayAttentionDetail(attention);
                textArea.setText(view.getTxtSearchPet().getText()); // Temporal, usar el detail construido
                
                // Reconstruir detalle para el diálogo
                displayAttentionDetail(attention);
                String fullDetail = view.getTableAttentions().getToolTipText(); // Usar el último construido
                
                JScrollPane scrollPane = new JScrollPane(textArea);
                detailDialog.add(scrollPane);
                
                detailDialog.setVisible(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,
                "Error al mostrar el detalle: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handlePrint() {
        if (currentPet == null) {
            JOptionPane.showMessageDialog(view,
                "Por favor, busque una mascota primero.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(view,
            "Funcionalidad de impresión en desarrollo.\nPróximamente disponible.",
            "Próximamente",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void handleExport() {
        if (currentPet == null) {
            JOptionPane.showMessageDialog(view,
                "Por favor, busque una mascota primero.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(view,
            "Funcionalidad de exportación a PDF en desarrollo.\nPróximamente disponible.",
            "Próximamente",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void handleClose() {
        mainWindow.setContent(mainWindow.getWelcomeView());
    }
}
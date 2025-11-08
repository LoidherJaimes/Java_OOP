package clinica_vet.controllers;

import clinica_vet.model.entities.Owner;
import clinica_vet.model.repositories.OwnerRepository;
import clinica_vet.views.OwnerManagementView;
import clinica_vet.views.CreateOwnerView;
import clinica_vet.views.EditOwnerView;
import clinica_vet.views.MainWindowView;

import javax.swing.*;
import java.util.UUID;

// ⭐ Ya no implementa OwnerTableRefreshable
public class OwnerManagementController { 
    
    private OwnerManagementView ownerManagementView;
    private OwnerRepository ownerRepository;
    private MainWindowView mainWindowViewOwner;
    private CreateEditOwnerController createEditController; 

    public OwnerManagementController(OwnerManagementView ownerManagementView, OwnerRepository ownerRepository, MainWindowView mainWindowViewOwner) {
        this.ownerManagementView = ownerManagementView;
        this.ownerRepository = ownerRepository;
        this.mainWindowViewOwner = mainWindowViewOwner;
        
        // ⭐ Pasamos una lambda (Runnable) que ejecuta loadOwnersIntoTable()
        Runnable tableRefresherAction = this::loadOwnersIntoTable;
        this.createEditController = new CreateEditOwnerController(ownerRepository, tableRefresherAction); 
        
        loadOwnersIntoTable();
        setupListeners();
    }

    // ⭐ El método de recarga es público para ser llamado por la lambda/Runnable
    public void loadOwnersIntoTable() {
        ownerManagementView.clearTable();
        for (Owner owner : ownerRepository.getAllOwners()) {
            ownerManagementView.addOwnerToTable(
                owner.getId(),
                owner.getName(),
                owner.getPhone(),
                owner.getAddress()
            );
        }
    }

    private void setupListeners() {
        this.ownerManagementView.getBtnClose().addActionListener(e -> {
            mainWindowViewOwner.setContent(mainWindowViewOwner.getWelcomeView());
        });
        
        this.ownerManagementView.getBtnCreate().addActionListener(e -> {
             launchCreateOwnerView();
        });

        this.ownerManagementView.getBtnEdit().addActionListener(e -> {
             launchEditOwnerView();
        });

        this.ownerManagementView.getBtnDelete().addActionListener(e -> {
            deleteSelectedOwner();
        });
    }
    
    // --- Lógica de Lanzamiento de Vistas ---

    private void launchCreateOwnerView() {
        CreateOwnerView createView = new CreateOwnerView(mainWindowViewOwner);
        createEditController.setupCreateView(createView);
        createView.setVisible(true);
    }

    private void launchEditOwnerView() {
        int selectedRow = ownerManagementView.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(ownerManagementView, "Por favor, seleccione un dueño para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object idObject = ownerManagementView.getTable().getValueAt(selectedRow, 0);
        UUID ownerId = (UUID) idObject;
        Owner ownerToEdit = ownerRepository.getOwnerById(ownerId);

        if (ownerToEdit != null) {
            EditOwnerView editView = new EditOwnerView(mainWindowViewOwner, ownerToEdit);
            createEditController.setupEditView(editView);
            editView.setVisible(true);
        } else {
             JOptionPane.showMessageDialog(ownerManagementView, "Dueño no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteSelectedOwner() {
        int selectedRow = ownerManagementView.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(ownerManagementView, "Por favor, seleccione un dueño para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object idObject = ownerManagementView.getTable().getValueAt(selectedRow, 0);
        UUID ownerId = (UUID) idObject;
        String ownerName = (String) ownerManagementView.getTable().getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(ownerManagementView,
            "¿Está seguro de eliminar al dueño: " + ownerName + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            ownerRepository.deleteOwnerById(ownerId);
            loadOwnersIntoTable(); // ⭐ Recarga directa
            JOptionPane.showMessageDialog(ownerManagementView, "Dueño eliminado exitosamente.");
        }
    }
}
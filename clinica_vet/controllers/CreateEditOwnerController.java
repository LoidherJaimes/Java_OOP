package clinica_vet.controllers;

import clinica_vet.model.entities.Owner;
import clinica_vet.model.repositories.OwnerRepository;
import clinica_vet.views.CreateOwnerView;
import clinica_vet.views.EditOwnerView;

import javax.swing.*;

public class CreateEditOwnerController {

    private final OwnerRepository ownerRepository;
    private final Runnable tableRefresher; // ⭐ Usamos Runnable para la acción de refrescar

    // ⭐ Constructor modificado para aceptar un Runnable
    public CreateEditOwnerController(OwnerRepository ownerRepository, Runnable tableRefresher) {
        this.ownerRepository = ownerRepository;
        this.tableRefresher = tableRefresher;
    }

    // Método para manejar la creación
    public void setupCreateView(CreateOwnerView view) {
        view.getBtnSave().addActionListener(e -> {
            String name = view.getNameTF().getText().trim();
            String phone = view.getPhoneTF().getText().trim();
            String address = view.getAddressTF().getText().trim();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Todos los campos son obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Owner newOwner = new Owner(name, phone, address);
            ownerRepository.addOwner(newOwner);

            JOptionPane.showMessageDialog(view, "Dueño creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();
            tableRefresher.run(); // ⭐ Ejecutar el Runnable (recargar la tabla)
        });
    }

    // Método para manejar la modificación
    public void setupEditView(EditOwnerView view) {
        view.getBtnSave().addActionListener(e -> {
            Owner ownerToEdit = view.getOwnerToEdit();
            String name = view.getNameTF().getText().trim();
            String phone = view.getPhoneTF().getText().trim();
            String address = view.getAddressTF().getText().trim();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Todos los campos son obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar el objeto Owner
            ownerToEdit.setName(name);
            ownerToEdit.setPhone(phone);
            ownerToEdit.setAddress(address);

            // Llamar al repositorio para actualizar
            ownerRepository.updateOwner(ownerToEdit);

            JOptionPane.showMessageDialog(view, "Dueño modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();
            tableRefresher.run(); // ⭐ Ejecutar el Runnable (recargar la tabla)
        });
    }
}
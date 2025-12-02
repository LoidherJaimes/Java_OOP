package clinica_vet.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReportsView extends JPanel {

    public final JTabbedPane tabbedPane;

    public final JComboBox<String> cbDoctors;
    public final JComboBox<String> cbAppointmentStatus;
    public final JButton btnRefreshAppointments;
    public final JTable tblAppointments;
    public final DefaultTableModel mdlAppointments;

    public final JButton btnRefreshTop;
    public final JTable tblTopReasons;
    public final DefaultTableModel mdlTopReasons;

    public final JTextField tfStartDate;
    public final JTextField tfEndDate;
    public final JButton btnRefreshIncome;
    public final JTable tblIncome;
    public final DefaultTableModel mdlIncome;
    public final JLabel lblIncomeNote;

    public ReportsView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("📊 Módulo de Reportes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(titleLabel, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();

        // TAB 1
        JPanel p1 = new JPanel(new BorderLayout());
        JPanel p1top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbDoctors = new JComboBox<>();
        cbAppointmentStatus = new JComboBox<>();
        cbAppointmentStatus.addItem("Todos");
        cbAppointmentStatus.addItem("PENDING");
        cbAppointmentStatus.addItem("CONFIRMED");
        cbAppointmentStatus.addItem("COMPLETED");
        btnRefreshAppointments = new JButton("Generar");

        p1top.add(new JLabel("Médico:"));
        p1top.add(cbDoctors);
        p1top.add(new JLabel("Estado:"));
        p1top.add(cbAppointmentStatus);
        p1top.add(btnRefreshAppointments);
        p1.add(p1top, BorderLayout.NORTH);

        mdlAppointments = new DefaultTableModel(new Object[]{"Médico", "Estado", "Cantidad"}, 0);
        tblAppointments = new JTable(mdlAppointments);
        p1.add(new JScrollPane(tblAppointments), BorderLayout.CENTER);

        tabbedPane.addTab("Citas por médico/estado", p1);

        // TAB 2
        JPanel p2 = new JPanel(new BorderLayout());
        JPanel p2top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnRefreshTop = new JButton("Generar Top");
        p2top.add(btnRefreshTop);
        p2.add(p2top, BorderLayout.NORTH);

        mdlTopReasons = new DefaultTableModel(new Object[]{"Motivo / Especie", "Tipo", "Cantidad"}, 0);
        tblTopReasons = new JTable(mdlTopReasons);
        p2.add(new JScrollPane(tblTopReasons), BorderLayout.CENTER);

        tabbedPane.addTab("Top motivos / especies", p2);

        // TAB 3
        JPanel p3 = new JPanel(new BorderLayout());
        JPanel p3top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        tfStartDate = new JTextField(10);
        tfEndDate = new JTextField(10);
        btnRefreshIncome = new JButton("Generar Ingresos");

        p3top.add(new JLabel("Desde:"));
        p3top.add(tfStartDate);
        p3top.add(new JLabel("Hasta:"));
        p3top.add(tfEndDate);
        p3top.add(btnRefreshIncome);

        p3.add(p3top, BorderLayout.NORTH);

        mdlIncome = new DefaultTableModel(new Object[]{"Periodo", "Cantidad órdenes", "Total"}, 0);
        tblIncome = new JTable(mdlIncome);
        p3.add(new JScrollPane(tblIncome), BorderLayout.CENTER);

        lblIncomeNote = new JLabel("Nota: si no existe un campo precio en MedicalOrder, aparecerá N/D.");
        p3.add(lblIncomeNote, BorderLayout.SOUTH);

        tabbedPane.addTab("Ingresos por período", p3);

        add(tabbedPane, BorderLayout.CENTER);
    }
}

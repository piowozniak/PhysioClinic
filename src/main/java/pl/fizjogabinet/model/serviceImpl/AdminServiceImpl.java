package pl.fizjogabinet.model.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import pl.fizjogabinet.model.dto.MedicalHistoryDTO;
import pl.fizjogabinet.model.entity.Patient;
import pl.fizjogabinet.model.entity.Therapist;
import pl.fizjogabinet.model.entity.User;
import pl.fizjogabinet.model.entity.Visit;
import pl.fizjogabinet.model.enums.FizjoGabinetFactoryE;
import pl.fizjogabinet.model.repository.PatientRepository;
import pl.fizjogabinet.model.repository.TherapistRepository;
import pl.fizjogabinet.model.service.AdminService;
import pl.fizjogabinet.model.service.ModelService;

@Service
public class AdminServiceImpl implements AdminService, ModelService {

	private final PatientRepository patientRepository;
	private final TherapistRepository therapistRepository;
	private List<Patient> allPatients = new ArrayList<>();
	private Patient patient;
	private List<MedicalHistoryDTO> listOfMedicalHistory = new ArrayList<>();
	private boolean displayVisits = false;
	private static final String USER = "user";

	@Autowired
	public AdminServiceImpl(PatientRepository patientRepository, TherapistRepository therapistRepository) {
		super();
		this.patientRepository = patientRepository;
		this.therapistRepository = therapistRepository;
	}

	@Override
	public String displayPatients(Model model) {
		allPatients = patientRepository.findAll();
		allPatients = new ArrayList<>(sortPatients(allPatients));
		model.addAttribute("allPatients", allPatients);
		return "patientspage";
	}

	@Override
	public String displayPatientsCard(Long id, Model model) {
		patient = patientRepository.findOne(id);
		Comparator<Visit> sortedVisits = Comparator.comparing(Visit::getDate).reversed();
		Collections.sort(patient.getVisits(), sortedVisits);
		listOfMedicalHistory.clear();
		patient.getMedicalHistory().forEach(m -> listOfMedicalHistory.add(new MedicalHistoryDTO(m)));
		model.addAttribute("patient", patient);
		model.addAttribute("listOfMedicalHistory", listOfMedicalHistory);
		return "patientscard";
	}

	private List<Patient> sortPatients(List<Patient> unsortedListOfPatients) {
		unsortedListOfPatients.sort(Comparator.comparing(Patient::getLastName, String::compareToIgnoreCase)
				.thenComparing(Patient::getFirstName, String::compareToIgnoreCase));
		return unsortedListOfPatients;
	}

	@Override
	public String displayMedicalHistory(Model model, Long id) {
		listOfMedicalHistory.forEach(m -> setToDisplayMedicalHistory(m, id));
		model.addAttribute("listOfMedicalHistory", listOfMedicalHistory);
		model.addAttribute("patient", patient);
		return "patientscard";
	}

	private void setToDisplayMedicalHistory(MedicalHistoryDTO medicalHistory, Long id) {
		if (medicalHistory.getMedicalHistory().getId().equals(id)) {
			medicalHistory.setDisplayMedicalHistory(true);
		} else {
			medicalHistory.setDisplayMedicalHistory(false);
		}
	}

	@Override
	public String displayVisits(Model model) {
		displayVisits = displayVisits ? false : true;
		model.addAttribute("displayVisits", displayVisits);
		model.addAttribute("listOfMedicalHistory", listOfMedicalHistory);
		model.addAttribute("patient", patient);
		return "patientscard";
	}

	@Override
	public void addAttributesToModel(Model model) {
		model.addAttribute("allPatients", allPatients);
		model.addAttribute("patient", patient);
	}

	@Override
	public String searchPatient(Model model, String search) {
		allPatients.clear();
		allPatients = patientRepository.findByName(search);
		allPatients = sortPatients(allPatients);
		model.addAttribute("allPatients", allPatients);
		return "patientspage";
	}

	@Override
	public String displayControlPanel(Model model) {
		Comparator<Therapist> comparator = Comparator.comparing(t -> t.getLastName());
		List<Therapist> therapists = therapistRepository.findByActiveTherapists();
		therapists.sort(comparator);
		User passwordForm = (User) FizjoGabinetFactoryE.objectFactory(USER);
		model.addAttribute("passwordForm", passwordForm);
		model.addAttribute("therapists", therapists);
		return "controlpanel";
	}

	public boolean isDisplayVisits() {
		return displayVisits;
	}

	public void setDisplayVisits(boolean displayVisits) {
		this.displayVisits = displayVisits;
	}

}

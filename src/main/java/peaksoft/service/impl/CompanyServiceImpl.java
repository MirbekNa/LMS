package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import peaksoft.dto.pagination.CompanyPagination;
import peaksoft.dto.request.CompanyRequest;
import peaksoft.dto.response.AboutCompany;
import peaksoft.dto.response.CompanyResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StudentResponse;
import peaksoft.entity.*;
import peaksoft.enums.Country;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.CompanyRepository;
import peaksoft.service.CompanyService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    public List<CompanyResponse> findAllCompanies() {
        return companyRepository.findAllCompanies();
    }

    @Override
    public SimpleResponse saveCompany(CompanyRequest companyRequest) {
        Company company = new Company();
        company.setName(companyRequest.name());
        company.setAddress(companyRequest.address());
        company.setCountry(companyRequest.country());
        company.setPhoneNumber(companyRequest.phoneNumber());
        companyRepository.save(company);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("successfully saved company with id " + company.getId())
                .build();
    }

    @Override
    public CompanyResponse findCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new
                NotFoundException("Company with id:" + id + " not found"));
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setName(company.getName());
        companyResponse.setAddress(company.getAddress());
        companyResponse.setCountry(company.getCountry());
        companyResponse.setPhoneNumber(company.getPhoneNumber());
        return companyResponse;
    }

    @Override
    public SimpleResponse updateCompany(Long id, CompanyRequest companyRequest) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "Company with id:" + id + " not found!"));
        company.setName(companyRequest.name());
        company.setAddress(companyRequest.address());
        company.setPhoneNumber(companyRequest.phoneNumber());
        company.setCountry(companyRequest.country());
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("successfully updated company with id: " + id)
                .build();
    }

    @Override
    public SimpleResponse updateCompanyMap(Long id, Map<String, Object> fields) {
        Company company = companyRepository.findById(id).orElseThrow(() ->
                new NotFoundException(
                        "Company with id:" + id + " not found!"));
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Company.class, key);
            if (field != null) {
                field.setAccessible(true);
                Object fieldValue = null;
                if (value instanceof String && field.getType() == String.class) {
                    fieldValue = value;
                } else if (value instanceof Country && field.getType() == Country.class) {
                    fieldValue = value;
                }
                if (fieldValue != null) {

                    ReflectionUtils.setField(field, company, value);
                }
            }
        });

        companyRepository.save(company);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated id: " + id + " ☺")
                .build();
    }

    @Override
    public SimpleResponse deleteCompany(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Company with id:" + id + " not found!!!"));
        companyRepository.delete(company);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Instructor with id:" + id + " successfully deleted ☺")
                .build();
    }

    @Override
    public AboutCompany getCompanyAboutById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Company with id:" + id + " not found !!!"));
        List<Course> courses = company.getCourses();
        List<String> courseName = new ArrayList<>();
        List<Instructor> instructors = company.getInstructors();
        List<String> instructorName = new ArrayList<>();
        List<Group> groups = new ArrayList<>();
        List<String> groupName = new ArrayList<>();
        int studentsOfNumber = 0;
        for (Course c : courses) {
            courseName.add(c.getCourseName());
            groups.addAll(c.getGroups());
        }
        for (Instructor i : instructors) {
            instructorName.add(i.getFirstName());
        }
        for (Group g : groups) {
            groupName.add(g.getGroupName());
            studentsOfNumber = g.getStudents().size();
        }
        AboutCompany aboutCompany = new AboutCompany();
        return aboutCompany.builder()
                .name(company.getName())
                .country(company.getCountry())
                .address(company.getAddress())
                .phoneNumber(company.getPhoneNumber())
                .courseNames(courseName)
                .instructorNames(instructorName)
                .groupNames(groupName)
                .allStudentsOfNumber(studentsOfNumber)
                .build();
    }

    @Override
    public List<StudentResponse> getAllStudentsOnlineEducation(Long id) {
        List<Student> allStudents = getAllStudents(id);
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Student s : allStudents) {
            if (s.getStudyFormat().name().equals("ONLINE")) {
                studentResponses.add(new StudentResponse(s.getId(), s.getFirstName(), s.getLastName(),
                        s.getPhoneNumber(), s.getEmail(), s.getStudyFormat(), s.getIsBlocked()));
            }
        }
        return studentResponses;
    }

    @Override
    public List<StudentResponse> getAllStudentsOfflineEducation(Long id) {
        List<Student> allStudents = getAllStudents(id);
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Student s : allStudents) {
            if (s.getStudyFormat().name().equals("OFFLINE")) {

                studentResponses.add(new StudentResponse(s.getId(), s.getFirstName(),
                        s.getLastName(), s.getPhoneNumber(), s.getEmail(), s.getStudyFormat(), s.getIsBlocked()));
            }
        }
        return studentResponses;
    }

    @Override
    public List<Student> getAllStudents(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Company with id:" + " not found !!!"));
        List<Course> courses = company.getCourses();
        List<Group> groups = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        for (Course c : courses) {
            groups.addAll(c.getGroups());
        }
        for (Group g : groups) {
            students.addAll(g.getStudents());
        }
        return students;
    }

    @Override
    public CompanyPagination getAllPaginationCompany(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<CompanyResponse> getAllCompanies = companyRepository.findAllCompanies(pageable);
        return CompanyPagination.builder()
                .companies(getAllCompanies.getContent())
                .currentPage(getAllCompanies.getNumber()+1)
                .pageSize(getAllCompanies.getTotalPages()-1)
                .build();

    }
}

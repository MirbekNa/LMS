package peaksoft.service;

import peaksoft.dto.pagination.CompanyPagination;
import peaksoft.dto.request.CompanyRequest;
import peaksoft.dto.response.AboutCompany;
import peaksoft.dto.response.CompanyResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StudentResponse;
import peaksoft.entity.Student;

import java.util.List;
import java.util.Map;

public interface CompanyService {
    List<CompanyResponse> findAllCompanies();

    SimpleResponse saveCompany(CompanyRequest companyRequest);

    CompanyResponse findCompanyById(Long id);

    SimpleResponse updateCompany(Long id, CompanyRequest companyRequest);

    SimpleResponse deleteCompany(Long id);

    SimpleResponse updateCompanyMap(Long id, Map<String, Object> fields);

    AboutCompany getCompanyAboutById(Long id);

    List<StudentResponse> getAllStudentsOnlineEducation(Long id);
    List<Student>getAllStudents(Long id);

    List<StudentResponse> getAllStudentsOfflineEducation(Long id);

    CompanyPagination getAllPaginationCompany(int currentPage, int pageSize);
}

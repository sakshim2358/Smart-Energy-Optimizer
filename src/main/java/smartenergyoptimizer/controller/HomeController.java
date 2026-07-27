package smartenergyoptimizer.controller;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import smartenergyoptimizer.EnergyData;
import smartenergyoptimizer.EnergyService;
import smartenergyoptimizer.dto.EnergyAnalysisResponse;
import smartenergyoptimizer.dto.EnergyDataDTO;
import org.springframework.data.domain.Page;
import java.util.List;
import com.sakshi.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/energy")
@CrossOrigin(origins = "*")
public class HomeController {
    private final EnergyService service;

    public HomeController(EnergyService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EnergyData>>> getAllData() {

        List<EnergyData> data = service.getAllData();

        ApiResponse<List<EnergyData>> response = new ApiResponse<>("Data fetched successfully", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EnergyData>> saveData(
            @Valid @RequestBody EnergyDataDTO data) {

        EnergyData savedData = service.saveData(data);

        ApiResponse<EnergyData> response = new ApiResponse<>("Data saved successfully", savedData);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/analysis")
    public ResponseEntity<EnergyAnalysisResponse> getAnalysis() {
        return ResponseEntity.ok(service.analyzeEnergyConsumption());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EnergyData>> updateData(
            @PathVariable Long id,
            @RequestBody EnergyData data) {

        EnergyData updatedData = service.updateData(id, data);

        ApiResponse<EnergyData> response = new ApiResponse<>("Data updated successfully", updatedData);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteData(@PathVariable Long id) {

        service.deleteData(id);

        ApiResponse<String> response = new ApiResponse<>("Data deleted successfully", "Success");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EnergyData>> searchByDeviceName(
            @RequestParam String deviceName) {

        return ResponseEntity.ok(service.searchByDeviceName(deviceName));
    }

    @GetMapping("/sort")
    public ResponseEntity<List<EnergyData>> sortByUnitsConsumed() {
        return ResponseEntity.ok(service.sortByUnitsConsumed());
    }

    @GetMapping("/search/date")
    public ResponseEntity<List<EnergyData>> searchByDate(
            @RequestParam String date) {

        return ResponseEntity.ok(service.searchByDate(date));
    }

    @GetMapping("/high-consumption")
    public ResponseEntity<List<EnergyData>> getHighConsumption(
            @RequestParam double unitsConsumed) {

        return ResponseEntity.ok(service.getHighConsumption(unitsConsumed));
    }

    @GetMapping("/sort/date")
    public ResponseEntity<List<EnergyData>> sortByDate() {
        return ResponseEntity.ok(service.sortByDate());
    }

    @GetMapping("/sort-desc")
    public ResponseEntity<List<EnergyData>> sortByUnitsConsumedDesc() {
        return ResponseEntity.ok(service.sortByUnitsConsumedDesc());
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<EnergyData>> getPaginatedData(
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(service.getPaginatedData(page, size));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<EnergyData>> getDataBetweenDates(
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return ResponseEntity.ok(
                service.getDataBetweenDates(startDate, endDate));
    }
    @GetMapping("/suggestion/{units}")
public String getSuggestion(@PathVariable double units) {
    return service.getEnergySavingSuggestion(units);
}
}

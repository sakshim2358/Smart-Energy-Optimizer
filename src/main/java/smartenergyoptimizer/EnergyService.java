package smartenergyoptimizer;

import org.springframework.stereotype.Service;

import smartenergyoptimizer.dto.EnergyAnalysisResponse;
import smartenergyoptimizer.dto.EnergyDataDTO;
import smartenergyoptimizer.exception.ResourceNotFoundException;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Service
public class EnergyService {

    private final EnergyRepository repository;

    public EnergyService(EnergyRepository repository) {
        this.repository = repository;
    }

    public List<EnergyData> getAllData() {
        return repository.findAll();
    }

    public EnergyData getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Energy record not found with id: " + id));
    }

    public EnergyData saveData(EnergyDataDTO dto) {

        EnergyData data = new EnergyData();

        data.setDeviceName(dto.getDeviceName());
        data.setUnitsConsumed(dto.getUnitsConsumed());
      double cost = dto.getUnitsConsumed() * 8;
data.setCost(cost);
        data.setDate(dto.getDate());

        return repository.save(data);
    }
public EnergyData updateData(Long id, EnergyData data) {

    data.setId(id);

    double cost = data.getUnitsConsumed() * 8;
    data.setCost(cost);

    return repository.save(data);
}
  

    public void deleteData(Long id) {
        repository.deleteById(id);
    }

    public EnergyAnalysisResponse analyzeEnergyConsumption() {

        List<EnergyData> energyList = repository.findAll();

        EnergyAnalysisResponse response = new EnergyAnalysisResponse();

        if (energyList.isEmpty()) {
            return response;
        }

        double totalConsumption = 0;

        for (EnergyData energy : energyList) {
            totalConsumption += energy.getUnitsConsumed();
        }

        response.setTotalConsumption(totalConsumption);

        double averageConsumption = totalConsumption / energyList.size();
        response.setAverageConsumption(averageConsumption);

        EnergyData highest = energyList.get(0);

        for (EnergyData energy : energyList) {
            if (energy.getUnitsConsumed() > highest.getUnitsConsumed()) {
                highest = energy;
            }
        }

        response.setHighestConsumingDevice(highest.getDeviceName());

        EnergyData lowest = energyList.get(0);

        for (EnergyData energy : energyList) {
            if (energy.getUnitsConsumed() < lowest.getUnitsConsumed()) {
                lowest = energy;
            }
        }

        response.setLowestConsumingDevice(lowest.getDeviceName());

        if (averageConsumption < 100) {
            response.setEnergyStatus("Efficient");
        } else if (averageConsumption <= 200) {
            response.setEnergyStatus("Moderate");
        } else {
            response.setEnergyStatus("High Consumption");
        }

        return response;
    }

    public List<EnergyData> searchByDeviceName(String deviceName) {
        return repository.findByDeviceName(deviceName);
    }

    public List<EnergyData> sortByUnitsConsumed() {
        return repository.findAll(Sort.by("unitsConsumed"));
    }

    public List<EnergyData> searchByDate(String date) {
        return repository.findByDate(date);
    }

    public List<EnergyData> getHighConsumption(double unitsConsumed) {
        return repository.findByUnitsConsumedGreaterThan(unitsConsumed);
    }

    public List<EnergyData> sortByDate() {
        return repository.findAll(Sort.by("date"));
    }

    public List<EnergyData> sortByUnitsConsumedDesc() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "unitsConsumed"));
    }

    public Page<EnergyData> getPaginatedData(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public List<EnergyData> getDataBetweenDates(String startDate, String endDate) {
        return repository.findByDateBetween(startDate, endDate);
    }public String getEnergySavingSuggestion(double units) {

    if (units > 40) {
        return "High energy usage detected. Reduce AC usage and switch off unused appliances.";
    } else if (units > 25) {
        return "Moderate energy usage. Use LED bulbs and avoid standby mode.";
    } else {
        return "Great! Your energy consumption is efficient. Keep it up.";
    }
}
}

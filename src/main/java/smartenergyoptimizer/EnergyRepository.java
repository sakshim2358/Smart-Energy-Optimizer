
package smartenergyoptimizer;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnergyRepository extends JpaRepository<EnergyData, Long> {
    List<EnergyData> findByDeviceName(String deviceName);

    List<EnergyData> findByDate(String date);

    List<EnergyData> findByUnitsConsumedGreaterThan(double unitsConsumed);

    List<EnergyData> findByDateBetween(String startDate, String endDate);
}
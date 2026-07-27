package smartenergyoptimizer.dto;

public class EnergyAnalysisResponse {
    private double totalConsumption;
    private double averageConsumption;
    private String highestConsumingDevice;
    private String lowestConsumingDevice;
    private String energyStatus;

    public EnergyAnalysisResponse() {
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public double getAverageConsumption() {
        return averageConsumption;
    }

    public void setAverageConsumption(double averageConsumption) {
        this.averageConsumption = averageConsumption;
    }

    public String getHighestConsumingDevice() {
        return highestConsumingDevice;
    }

    public void setHighestConsumingDevice(String highestConsumingDevice) {
        this.highestConsumingDevice = highestConsumingDevice;
    }

    public String getLowestConsumingDevice() {
        return lowestConsumingDevice;
    }

    public void setLowestConsumingDevice(String lowestConsumingDevice) {
        this.lowestConsumingDevice = lowestConsumingDevice;
    }

    public String getEnergyStatus() {
        return energyStatus;
    }

    public void setEnergyStatus(String energyStatus) {
        this.energyStatus = energyStatus;
    }
    
}

public class GreenLightEvent extends Event{

    private RoadData roadData;

    public GreenLightEvent(Integer eventType, Integer timeStep, RoadData roadData) {
        super(eventType, timeStep);
        this.roadData = roadData;
    }

    @Override
    public void changeGreenStatus() {
        roadData.setGreenStatus(!roadData.isGreenStatus());
    }
}

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GoogleMapHTMLGenerator {

    public GoogleMapHTMLGenerator(List<String[]> coordinatesList){
        generateHTMLFile(coordinatesList);
        runHtmlFile();
    }

    private static String generateMapHtml(List<String[]> coordinatesList) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<title>Google Maps Route</title>");
        html.append("<script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyCBV-rVdRrqk8XLs4yJEks-p9BJYnuI72g\"></script>");
        html.append("<script>");
        html.append("function initMap() {");
        html.append("var map = new google.maps.Map(document.getElementById('map'), {");
        html.append("zoom: 6,");
        // Center the map on the first coordinate
        html.append("center: { lat: ").append(Double.parseDouble(coordinatesList.get(0)[0])).append(", lng: ").append(Double.parseDouble(coordinatesList.get(0)[1])).append(" }");
        html.append("});");
        // Create a polyline to represent the route
        html.append("var routeCoordinates = [");
        for (String[] coordinate : coordinatesList) {
            html.append("{ lat: ").append(Double.parseDouble(coordinate[0])).append(", lng: ").append(Double.parseDouble(coordinate[1])).append(" },");
        }
        html.append("];");
        html.append("var routePath = new google.maps.Polyline({");
        html.append("path: routeCoordinates,");
        html.append("geodesic: true,");
        html.append("strokeColor: '#FF0000',");
        html.append("strokeOpacity: 1.0,");
        html.append("strokeWeight: 2");
        html.append("});");
        html.append("routePath.setMap(map);");
        html.append("}");
        html.append("</script>");
        html.append("</head>");
        html.append("<body onload=\"initMap()\">");
        html.append("<div id=\"map\" style=\"height: 700px; width: 100%;\"></div>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    private void generateHTMLFile(List<String[]> coordinatesList){
        String htmlContent = generateMapHtml(coordinatesList);

        try (FileWriter writer = new FileWriter("map.html")) {
            writer.write(htmlContent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void runHtmlFile(){
        try {
            File htmlFile = new File("G:\\Shared drives\\yntp\\CS461- Introduction to AI\\Assignment\\map.html");
            if (htmlFile.exists()) {
                Desktop.getDesktop().open(htmlFile);
            } else {
                System.out.println("HTML file does not exist.");
            }
        } catch (IOException e) {
            System.out.println("Error opening HTML file: " + e.getMessage());
        }
    }
}

public class NBody {
    /**
     * Read txt file from the path and return radius of the universe.
     * @param filePath Path of the txt file
     * @return double: radius of the universe
     */
    public static double readRadius(String filePath) {
        In in = new In(filePath);
        int numPlanets = in.readInt();
        double radius = in.readDouble();
        return radius;
    }


    /**
     * Read in all the parameters of the planets and return an array of planets.
     * @param filePath Path of the txt file
     * @return Planet[] array of all planets
     */
    public static Planet[] readPlanets(String filePath) {
        In in = new In(filePath);
        int numPlanets = in.readInt();
        double radius = in.readDouble();
        Planet[] planets = new Planet[numPlanets];
        for (int i = 0; i < numPlanets; i++) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xP, yP, xV, yV, m, img);
        }
        return planets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] planets = readPlanets(filename);
        // read in the radius of the universe
        double radius = readRadius(filename);
        // create a canvas and set the scale so it matches the radius
        StdDraw.setScale(-radius, radius);
        // clear the drawing window
        StdDraw.clear();
        // Draw the background
        String BACKGROUND = "images/starfield.jpg";
        StdDraw.picture(0, 0, BACKGROUND);

        // Draw the planets
        for (Planet p : planets) {
            p.draw();
        }
        StdDraw.enableDoubleBuffering();
        double time = 0;

        while (time < T) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            // calculate net force for each planet
            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            // update each planet
            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            // Draw background and new planet position
            StdDraw.picture(0, 0, BACKGROUND);
            for (Planet p : planets) {
                p.draw();
            }
            StdDraw.show();
            // Pause 10ms
            StdDraw.pause(10);
            time += dt;
        }
    }
}

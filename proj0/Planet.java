/**
 *  Basic class for planet object.
 *  @author Yuqi Yan
 */
public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static double GRAVITY_CONST = 6.67e-11;

    /**
     * Constructor with parameters
     * @param xP Position of x
     * @param yP Position of y
     * @param xV Velocity of x-axis
     * @param yV Velocity of y-axis
     * @param m Mass of the planet
     * @param img Path of the image of the planet
     */
    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    /**
     * Constructor with another Planet object
     * @param p Planet object to be copied
     */
    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    /**
     * Calculate the distance between two planets.
     * The formula is: r ^ 2 = dx ^ 2 + dy ^ 2
     * @param p The planet we want to calculate the distance from
     * @return double: distance between this planet and the other
     */
    public double calcDistance(Planet p) {
        double dx = Math.abs(this.xxPos - p.xxPos);
        double dy = Math.abs(this.yyPos - p.yyPos);
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Calculate the force exerted by the other planet.
     * The formula is: G * m1 * m2 / r ^ 2
     * @param p The planet we want to calculate the force exerted by
     * @return double: the force exerted by the planet
     */
    public double calcForceExertedBy(Planet p) {
        double distance = this.calcDistance(p);
        return GRAVITY_CONST * this.mass * p.mass / Math.pow(distance, 2);
    }

    /**
     * Calculate the force exerted by the other planet on the x-axis
     * The formula is: Fx = F * dx / r
     * @param p The planet we want to calculate the force exerted by on the x-axis
     * @return double: the force exerted by the planet on the x-axis
     */
    public double calcForceExertedByX(Planet p) {
        double distance = this.calcDistance(p);
        double dx = p.xxPos - this.xxPos;
        return this.calcForceExertedBy(p) * dx / distance;
    }

    /**
     * Calculate the force exerted by the other planet on the y-axis
     * The formula is: Fy = F * dy / r
     * @param p The planet we want to calculate the force exerted by on the y-axis
     * @return double: the force exerted by the planet on the y-axis
     */
    public double calcForceExertedByY(Planet p) {
        double distance = this.calcDistance(p);
        double dy = p.yyPos - this.yyPos;
        return this.calcForceExertedBy(p) * dy / distance;
    }


    /**
     * Calculate the net force exerted by planets on the x-axis
     * @param ps All the planets to be calculated
     * @return double: net force exerted by planets on the x-axis
     */
    public double calcNetForceExertedByX(Planet[] ps) {
        double netFx = 0;
        for (Planet p : ps) {
            if (!this.equals(p)) {
                netFx += this.calcForceExertedByX(p);
            }
        }
        return netFx;
    }

    /**
     * Calculate the net force exerted by planets on the y-axis
     * @param ps All the planets to be calculated
     * @return double: net force exerted by planets on the y-axis
     */
    public double calcNetForceExertedByY(Planet[] ps) {
        double netFy = 0;
        for (Planet p : ps) {
            if (!this.equals(p)) {
                netFy += this.calcForceExertedByY(p);
            }
        }
        return netFy;
    }

    /**
     * Update the position and velocity of the planet when a certain force
     * is applied to it.
     * @param dt Time of the force applied to the planet
     * @param xF Force applied to the planet on the x-axis
     * @param yF Force applied to the planet on the y-axis
     */
    public void update(double dt, double xF, double yF) {
        double xA = xF / this.mass;
        double yA = yF / this.mass;
        // update the position and velocity
        this.xxVel = this.xxVel + dt * xA;
        this.yyVel = this.yyVel + dt * yA;
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel;
    }

    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }
}

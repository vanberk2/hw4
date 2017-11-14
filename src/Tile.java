public class Tile {
    private String image;
    private Boolean ship;
    private Boolean hit;

    public Tile(String image, Boolean isShip, Boolean isHit) {
        this.image = image;
        this.ship = isShip;
        this.hit = isHit;
    }

    public String getImage() {
        return image;
    }

    public Boolean isShip() {
        return ship;
    }

    public Boolean isHit() {
        return hit;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setIsShip(Boolean ship) {
        ship = ship;
    }

    public void setHit(Boolean hit) {
        if (hit) {
            if (this.image.equals("batt1.gif")) {
                this.image = "batt201.gif";
            } else if (this.image.equals("batt2.gif") || this.image.equals("batt3.gif") || this.image.equals("batt4.gif")) {
                this.image = "batt202.gif";
            } else if (this.image.equals("batt5.gif")) {
                this.image = "batt203.gif";
            } else if (this.image.equals("batt6.gif")) {
                this.image = "batt204.gif";
            } else if (this.image.equals("batt7.gif") || this.image.equals("batt8.gif") || this.image.equals("batt9.gif")) {
                this.image = "batt205.gif";
            } else if (this.image.equals("batt10.gif")) {
                this.image = "batt206.gif";
            }
            this.hit = hit;
        }
    }
}

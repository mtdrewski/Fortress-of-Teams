package model.units;

public enum UnitTeam{
    LEFT{
        public UnitTeam otherTeam(){
            return RIGHT;
        }
        public int getDirection(){
            return 1;
        }
        public String getHexColor() { return "#c72434";}
    },
    RIGHT{
        public UnitTeam otherTeam(){
            return LEFT;
        }
        public int getDirection(){
            return -1;
        }
        public String getHexColor() { return "#488bf7";}
    };

    public abstract UnitTeam otherTeam();
    public abstract int getDirection();
    public abstract String getHexColor();
}

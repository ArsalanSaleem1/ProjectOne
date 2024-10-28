public class Door {
    private boolean isWin;
    private boolean isOpened;

    // setters

    public void setWin(boolean win) {
        isWin = win;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }


    // getters

    public boolean isWin() {
        return isWin;
    }

    public boolean isLoss() {
        return !this.isWin();
    }

    public boolean isOpened() {
        return isOpened;
    }

    public boolean isClosed() {
        return !this.isOpened();
    }
}
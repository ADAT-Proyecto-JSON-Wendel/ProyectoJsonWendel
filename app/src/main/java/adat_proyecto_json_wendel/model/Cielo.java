package model;

public class Cielo {
        private int manha;
        private int tarde;
        private int noite;
        public int getManha() {
            return manha;
        }
        public void setManha(int manha) {
            this.manha = manha;
        }
        public int getTarde() {
            return tarde;
        }
        public void setTarde(int tarde) {
            this.tarde = tarde;
        }
        public int getNoite() {
            return noite;
        }
        public void setNoite(int noite) {
            this.noite = noite;
        }
        @Override
        public String toString() {
            return "Cielo [manha=" + manha + ", tarde=" + tarde + ", noite=" + noite + "]";
        }

        
    }
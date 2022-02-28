package com.index.hadoop;

public class TfIdf implements Comparable {
    private String docId;
    private float score;

    public TfIdf(String docId, float score) {
        this.docId = docId;
        this.score = score;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public int compareTo(Object o) {
        return Float.compare(((TfIdf) o).getScore(), this.getScore());
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;

        if (object != null && object instanceof TfIdf) {
            isEqual = (this.docId.equals(((TfIdf) object).getDocId()));
        }

        return isEqual;
    }
}
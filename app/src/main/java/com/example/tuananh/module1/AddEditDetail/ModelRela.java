package com.example.tuananh.module1.AddEditDetail;

import com.example.tuananh.module1.Model.Model;

public class ModelRela {
    public String relationship;
    public Model model;

    public ModelRela(String relationship, Model model) {
        this.relationship = relationship;
        this.model = model;
    }

    public ModelRela(Model model) {
        this.model = model;
    }

    public ModelRela() {
    }
}

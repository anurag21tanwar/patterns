package com.upgrad.patterns.Service;

import com.upgrad.patterns.Constants.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.upgrad.patterns.Constants.SourceType.DiseaseSh;
import static com.upgrad.patterns.Constants.SourceType.JohnHopkins;

@Service
public class DiseaseCountFacade {

    private IndiaDiseaseStatFactory indiaDiseaseStat;

    @Autowired
    public DiseaseCountFacade(IndiaDiseaseStatFactory indiaDiseaseStat)
    {
        this.indiaDiseaseStat = indiaDiseaseStat;
    }

    public Object getDiseaseShCount(){
        return indiaDiseaseStat.GetInstance(DiseaseSh).GetActiveCount();
    }

    public Object getJohnHopkinCount () {
        return indiaDiseaseStat.GetInstance(JohnHopkins).GetActiveCount();
    }

    public Object getInfectedRatio(String sourceType) throws IllegalArgumentException {
        try {
            Float population = 900000000F;
            SourceType sourceEnum = SourceType.valueOf(sourceType);
            Float active = Float.valueOf(indiaDiseaseStat.GetInstance(sourceEnum).GetActiveCount());
            Float percent = Float.valueOf((active / population));
            return String.format("%.3f", percent * 100);
        }
        catch (Exception e) {
            String message = String.format("Invalid source type specified. Available source type (%s, %s)", SourceType.DiseaseSh, SourceType.JohnHopkins);
            throw new IllegalArgumentException(message);
        }
    }
}

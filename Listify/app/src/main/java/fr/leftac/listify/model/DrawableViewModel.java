package fr.leftac.listify.model;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DrawableViewModel extends ViewModel {
    private final MutableLiveData<Drawable> drawableToShow = new MutableLiveData<Drawable>();
    public void setDrawableToShow(Drawable drawable) {
        drawableToShow.setValue(drawable);
    }
    public LiveData<Drawable> getSelectedItem() {
        return drawableToShow;
    }
}

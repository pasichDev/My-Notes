package com.pasich.mynotes.utils.adapters.genericAdapterNote;

import androidx.databinding.ViewDataBinding;

public interface GenericAdapterCallback<VM extends ViewDataBinding, T> {
    void bindData(VM binder, T model);
}
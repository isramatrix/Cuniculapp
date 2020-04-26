package upv.cuniculappteam.cuniculapp.activity.farms.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.SortedList;

import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import upv.cuniculappteam.cuniculapp.R;
import upv.cuniculappteam.cuniculapp.activity.farms.cycles.CycleActivity;
import upv.cuniculappteam.cuniculapp.activity.utils.ModelLifecycleFragment;
import upv.cuniculappteam.cuniculapp.activity.utils.NamedFragment;
import upv.cuniculappteam.cuniculapp.logic.lists.Sorts;
import upv.cuniculappteam.cuniculapp.model.Cycle;
import upv.cuniculappteam.cuniculapp.model.Replacement;
import upv.cuniculappteam.cuniculapp.model.facilities.Farm;
import upv.cuniculappteam.cuniculapp.view.farms.adapters.CyclesAdapter;
import upv.cuniculappteam.cuniculapp.view.farms.dialogs.CycleDialog;
import upv.cuniculappteam.cuniculapp.view.farms.dialogs.CycleDialog.Result;
import upv.cuniculappteam.cuniculapp.view.utils.dialog.DialogForResult.Header;
import upv.cuniculappteam.cuniculapp.view.utils.recycler.SelectableAdapter;
import upv.cuniculappteam.cuniculapp.viewmodel.CycleViewModel;

public class CyclesFragment extends ModelLifecycleFragment<Cycle> implements
        NamedFragment
{
    private final Farm farm;

    private CycleViewModel cycles;

    CyclesFragment(Farm farm) { this.farm = farm; }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_cycles, container, false);
    }

    /**
     * Inicializa las vistas del fragmento de los ciclos de una granja.
     *
     * @param view La vista raíz del fragmento.
     * @param savedInstanceState El estado de la instancia de la aplicación.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        cycles = ViewModelProviders.of(this).get(CycleViewModel.class);
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Obtiene una instancia concreta del adaptador principal del fragmento que muestra
     * los elementos seleccionables.
     *
     * @return Una instancia de un adaptador multiselección
     */
    @Override
    public SelectableAdapter<Cycle> getAdapter()
    {
        return new CyclesAdapter();
    }

    /**
     * Obtiene el identificador del recurso de la vista de reciclaje donde se muestran los
     * elementos del fragmento.
     *
     * @return El identificador del recurso.
     */
    @Override
    public int getAdapterId()
    {
        return R.id.recycler_cycles;
    }

    /**
     * Obtiene una tarea programada de la que se obtienen los elementos a mostrar en el
     * adaptador de elementos principal del fragmento.
     *
     * @return Una tarea para obtener elementos.
     */
    @Override
    public Task<List<Cycle>> getAdapterData()
    {
        return cycles.getCycles(farm).continueWith((task) -> Sorts.sort(task.getResult()));
    }

    /**
     * Obtiene una tarea programada en la que se eliminan los elementos seleccionados del
     * adaptador de elementos principal del fragmento.
     *
     * @param cycles Los elementos a eliminar.
     *
     * @return La tarea en la que se eliminan dichos elementos.
     */
    @Override
    public Task<List<Cycle>> onDeleteSelected(Collection<Cycle> cycles)
    {
        return this.cycles.deleteCycles(cycles).continueWithTask(
                (t) -> this.cycles.getCycles(farm)
        );
    }

    /**
     * Maneja el control de la vista de reciclaje para cambiar de actividad cuando
     * un ciclo concreta ha sido seleccionado.
     *
     * @param cycle El ciclo seleccionado.
     */
    @Override
    public void onItemClicked(Cycle cycle)
    {
        Intent intent = new Intent(getActivity(), CycleActivity.class);
        intent.putExtra(CycleActivity.CYCLE_INTENT_KEY, (Parcelable) cycle);
        startActivity(intent);
    }

    /**
     * Obtiene una instancia de un diálogo donde se recogen los atributos de un nuevo elemento
     * a añadir en el adaptador de elementos principal del fragmento.
     *
     * @return Una instancia de un diálogo.
     */
    @Override
    public DialogFragment getAddDialog()
    {
        return new CycleDialog(Header.ADD, (r) -> createItem(makeCycle(r)));
    }

    private Task<List<Cycle>> makeCycle(Result result)
    {
        Cycle cycle = new Cycle();
        cycle.setDate(result.getDate());
        cycle.setFarm(farm.getId());

        return cycles.addCycle(cycle).continueWithTask(
                (t) -> cycles.getCycles(farm)
        );
    }

    /**
     * Obtiene el nombre del fragmento en ejecución.
     *
     * @return El identificador del recurso con el nombre del fragmento.
     */
    @Override
    public int getFragmentName() { return R.string.main_cycles; }
}
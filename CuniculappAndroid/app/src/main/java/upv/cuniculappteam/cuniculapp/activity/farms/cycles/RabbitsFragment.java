package upv.cuniculappteam.cuniculapp.activity.farms.cycles;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Date;

import upv.cuniculappteam.cuniculapp.R;
import upv.cuniculappteam.cuniculapp.activity.utils.NamedFragment;
import upv.cuniculappteam.cuniculapp.model.Cycle;
import upv.cuniculappteam.cuniculapp.model.animals.Kitten;
import upv.cuniculappteam.cuniculapp.model.animals.KittenChange;
import upv.cuniculappteam.cuniculapp.model.animals.Mother;
import upv.cuniculappteam.cuniculapp.model.animals.MotherChange;
import upv.cuniculappteam.cuniculapp.view.farms.dialogs.KittenDialog;
import upv.cuniculappteam.cuniculapp.view.farms.dialogs.MotherDialog;
import upv.cuniculappteam.cuniculapp.view.utils.LoadingView;
import upv.cuniculappteam.cuniculapp.viewmodel.RabbitViewModel;

import static upv.cuniculappteam.cuniculapp.view.utils.dialog.DialogForResult.Header;

public class RabbitsFragment extends Fragment implements NamedFragment
{
    private static final String ADD_MOTHER_TAG = "AddMother";

    private static final String ADD_KITTEN_TAG = "AddKitten";

    private static final String REMOVE_MOTHER_TAG = "RemoveMother";

    private static final String REMOVE_KITTEN_TAG = "RemoveKitten";

    private final Cycle cycle;

    private RabbitViewModel rabbits;

    private View view;

    private Kitten kittens;

    private Mother mothers;

    RabbitsFragment(Cycle cycle) { this.cycle = cycle; }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_rabbits, container, false);
    }

    /**
     * Inicializa las vistas del fragmento de los conejos de una granja.
     *
     * @param view La vista raíz del fragmento.
     * @param savedInstanceState El estado de la instancia de la aplicación.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(this.view = view, savedInstanceState);

        this.rabbits = ViewModelProviders.of(this).get(RabbitViewModel.class);

        // Se muestran los datos de los conejos asociados al ciclo.
        updateView();

        // Se inicializan los botones que lanzan las ventanas emergentes.
        view.findViewById(R.id.rabbits_mother_add_button).setOnClickListener(this::openAddMother);
        view.findViewById(R.id.rabbits_mother_remove_button).setOnClickListener(this::openRemoveMother);
        view.findViewById(R.id.rabbits_kitten_add_button).setOnClickListener(this::openAddKitten);
        view.findViewById(R.id.rabbits_kitten_remove_button).setOnClickListener(this::openRemoveKitten);
    }

    /**
     * Realiza las llamadas al VM de conejos para actualizar la vista de los datos a visualizar en
     * la actividad actual.
     */
    private void updateView(Object... params)
    {
        LoadingView.show(getActivity());
        rabbits.getMothers(cycle)
                .addOnCompleteListener(LoadingView::hide)
                .addOnSuccessListener(this::showMothers)
                .addOnFailureListener(this::showFailedDialog);

        LoadingView.show(getActivity());
        rabbits.getKittens(cycle)
                .addOnCompleteListener(LoadingView::hide)
                .addOnSuccessListener(this::showKittens)
                .addOnFailureListener(this::showFailedDialog);
    }

    /**
     * Inicializa e infla los datos de vista sobre las madres pertenecientes al ciclo
     * que se está gestionando.
     *
     * @param mothers La instancia de madres del ciclo
     */
    private void showMothers(Mother mothers)
    {
        this.mothers = mothers;

        TextView mothersAlive = view.findViewById(R.id.rabbits_mothers_alive_text);
        mothersAlive.setText(String.valueOf(mothers.getAlive()));

        TableLayout jailsTable = view.findViewById(R.id.rabbits_mother_jails_table);
        // TODO: Inflar los datos de esta vista.
    }

    /**
     * Inicializa e infla los datos de vista sobre los gazapos pertenecientes al ciclo
     * que se está gestionando.
     *
     * @param kittens La instancia de gazapos del ciclo
     */
    private void showKittens(Kitten kittens)
    {
        this.kittens = kittens;

        TextView kittensAlive = view.findViewById(R.id.rabbits_kittens_alive_text);
        kittensAlive.setText(String.valueOf(kittens.getMaternal() + kittens.getMeat()));

        TableLayout jailsTable = view.findViewById(R.id.rabbits_kitten_jails_table);
        // TODO: Inflar los datos de esta vista.
    }

    /**
     * Infla la vista del menú cabecera con los botones para ver el histórico de
     * conejos del ciclo que se está gestionando.
     *
     * @param menu El menú cabecera.
     * @param inflater El objeto mediador para inflar la vista de menú.
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.action_history_items, menu);
    }

    /**
     * Maneja el control de la vista de menú en función del botón de menú
     * seleccionado.
     *
     * @param item El botón de menú seleccionado.
     *
     * @return Si el control ha sido manejado correctamente.
     */
    @Override @CallSuper
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId()) {
            // Si se quiere ver el histórico de conejos, cambia de actividad.
            case R.id.action_history: showHistory(); return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Inicia una actividad de histórico para mostrar los conejos asociados al
     * ciclo de la granja desde su existencia.
     */
    private void showHistory()
    {
        // Si los datos de madres y gazapos no han sido recuperados de base de datos, no realiza ningún acción
        if (mothers == null || kittens == null) return;

        Intent intent = new Intent(getActivity(), CycleHistoryActivity.class);
        intent.putExtra(CycleHistoryActivity.MOTHERS_INTENT_KEY, (Parcelable) mothers);
        intent.putExtra(CycleHistoryActivity.KITTENS_INTENT_KEY, (Parcelable) kittens);
        startActivity(intent);
    }

    /**
     * Crea y muestra un diálogo específico que informa que alguna transacción de
     * elementos no ha podido ser efectuada.
     */
    private void showFailedDialog(Object... params)
    {
        if (getActivity() == null) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_header_error)
                .setMessage(R.string.dialog_error_text)
                .setPositiveButton(R.string.dialog_error_confirm, null);

        builder.create().show();
    }

    /**
     * Inicializa y muestra un diálogo para introducir los datos de una adición
     * de conejos madre a la base de datos.
     */
    private void openAddMother(View view)
    {
        MotherDialog dialog = new MotherDialog(Header.ADD, (r) -> {
            LoadingView.show(getActivity());
            rabbits.addMother(makeMotherChange(r))
                    .addOnCompleteListener(LoadingView::hide)
                    .addOnSuccessListener(this::updateView)
                    .addOnFailureListener(LoadingView::hide);
        });

        if (getFragmentManager() != null)
            dialog.show(getFragmentManager(), ADD_MOTHER_TAG);
    }

    /**
     * Inicializa y muestra un diálogo para introducir los datos de un borrado
     * de conejos madre a la base de datos.
     */
    private void openRemoveMother(View view)
    {
        MotherDialog dialog = new MotherDialog(Header.REMOVE, (r) -> {
            LoadingView.show(getActivity());
            rabbits.removeMother(makeMotherChange(r))
                    .addOnCompleteListener(LoadingView::hide)
                    .addOnSuccessListener(this::updateView)
                    .addOnFailureListener(LoadingView::hide);
        });

        if (getFragmentManager() != null)
            dialog.show(getFragmentManager(), REMOVE_MOTHER_TAG);
    }

    private MotherChange makeMotherChange(MotherDialog.Result result)
    {
        MotherChange change = new MotherChange();
        change.setAmount(result.getAmount());
        change.setReason(result.getReason());
        change.setDate(new Date(System.currentTimeMillis()));
        change.setMother(this.mothers.getId());
        return change;
    }

    /**
     * Inicializa y muestra un diálogo para introducir los datos de una adición
     * de conejos gazapos a la base de datos.
     */
    private void openAddKitten(View view)
    {
        KittenDialog dialog = new KittenDialog(Header.ADD, (r) -> {
            LoadingView.show(getActivity());
            rabbits.addKitten(makeKittenChange(r))
                    .addOnCompleteListener(LoadingView::hide)
                    .addOnSuccessListener(this::updateView)
                    .addOnFailureListener(LoadingView::hide);
        });

        if (getFragmentManager() != null)
            dialog.show(getFragmentManager(), ADD_KITTEN_TAG);
    }

    /**
     * Inicializa y muestra un diálogo para introducir los datos de un borrado
     * de conejos gazapos a la base de datos.
     */
    private void openRemoveKitten(View view)
    {
        KittenDialog dialog = new KittenDialog(Header.REMOVE, (r) -> {
            LoadingView.show(getActivity());
            rabbits.removeKitten(makeKittenChange(r))
                    .addOnCompleteListener(LoadingView::hide)
                    .addOnSuccessListener(this::updateView)
                    .addOnFailureListener(LoadingView::hide);
        });

        if (getFragmentManager() != null)
            dialog.show(getFragmentManager(), REMOVE_KITTEN_TAG);
    }

    private KittenChange makeKittenChange(KittenDialog.Result result)
    {
        KittenChange change = new KittenChange();
        change.setReason(result.getReason());
        change.setKittens(kittens.getId());
        change.setMaternalAmount(result.getMaternalNest() + result.getMaternalBait());
        change.setMeatAmount(result.getMeetBait() + result.getMeetNest());
        change.setDate(new Date(System.currentTimeMillis()));
        return change;
    }

    @Override
    public int getFragmentName() {
        return R.string.main_rabbits;
    }
}
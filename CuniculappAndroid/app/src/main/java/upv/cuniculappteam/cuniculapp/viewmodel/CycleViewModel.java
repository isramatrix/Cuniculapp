package upv.cuniculappteam.cuniculapp.viewmodel;

import android.view.ViewStructure;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import upv.cuniculappteam.cuniculapp.logic.firebase.Database;
import upv.cuniculappteam.cuniculapp.logic.firebase.Firebase;
import upv.cuniculappteam.cuniculapp.model.Cycle;
import upv.cuniculappteam.cuniculapp.model.Labor;
import upv.cuniculappteam.cuniculapp.model.facilities.Farm;
import upv.cuniculappteam.cuniculapp.model.labors.Insemination;
import upv.cuniculappteam.cuniculapp.model.labors.Labour;
import upv.cuniculappteam.cuniculapp.model.labors.Palpation;
import upv.cuniculappteam.cuniculapp.model.labors.Sale;

public class CycleViewModel extends ViewModel
{
    public Task<List<Cycle>> getCycles(Farm farm)
    {
        return Firebase.Database.fetchWhere("farm", farm.getId(), Cycle.class);
    }

    public Task<Void> deleteCycles(Collection<Cycle> cycles)
    {
        List<Task<?>> tasks = new ArrayList<>();
        for (Cycle cycle : cycles)
        {
            tasks.add(Firebase.Database.delete(Cycle.class, cycle));
            tasks.add(Firebase.Database.delete(Cycle.class, cycle));
        }
        return Tasks.whenAll(tasks);
    }

    public Task<Void> addCycle(Cycle cycle)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(cycle.getDate());

        calendar.add(Calendar.DAY_OF_YEAR, -5);
        Labor photoperiodStart = new Labor();
        photoperiodStart.setDeadline(calendar.getTime());
        photoperiodStart.setPriority(Labor.Priority.LOW);
        photoperiodStart.setState(Labor.State.TO_DO);
        photoperiodStart.setManual(false);
        photoperiodStart.setTaskType(Labor.TaskType.PHOTOPERIOD_START);
        Task<String> photoperiodStartTask = Firebase.Database.add(Labor.class, photoperiodStart);

        calendar.add(Calendar.DAY_OF_YEAR, 4);
        Labor photoperiodEnd = new Labor();
        photoperiodEnd.setDeadline(calendar.getTime());
        photoperiodEnd.setPriority(Labor.Priority.LOW);
        photoperiodEnd.setState(Labor.State.TO_DO);
        photoperiodEnd.setManual(false);
        photoperiodEnd.setTaskType(Labor.TaskType.PHOTOPERIOD_END);
        Task<String> photoperiodEndTask = Firebase.Database.add(Labor.class, photoperiodEnd);

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Labor inseminationLabor = new Labor();
        inseminationLabor.setDeadline(calendar.getTime());
        inseminationLabor.setPriority(Labor.Priority.LOW);
        inseminationLabor.setState(Labor.State.TO_DO);
        inseminationLabor.setManual(false);
        inseminationLabor.setTaskType(Labor.TaskType.INSEMINATION);
        Task<String> inseminationTask = Firebase.Database.add(Labor.class, inseminationLabor);

        calendar.add(Calendar.DAY_OF_YEAR, 8);
        Labor palpableRabbitsStart = new Labor();
        palpableRabbitsStart.setDeadline(calendar.getTime());
        palpableRabbitsStart.setPriority(Labor.Priority.LOW);
        palpableRabbitsStart.setState(Labor.State.TO_DO);
        palpableRabbitsStart.setManual(false);
        palpableRabbitsStart.setTaskType(Labor.TaskType.PALPABLE_RABBITS_START);
        Task<String> palpableRabbitsStartTask = Firebase.Database.add(Labor.class, palpableRabbitsStart);

        calendar.add(Calendar.DAY_OF_YEAR, 11);
        Labor palpableRabbitsFinish = new Labor();
        palpableRabbitsFinish.setDeadline(calendar.getTime());
        palpableRabbitsFinish.setPriority(Labor.Priority.LOW);
        palpableRabbitsFinish.setState(Labor.State.TO_DO);
        palpableRabbitsFinish.setManual(false);
        palpableRabbitsFinish.setTaskType(Labor.TaskType.PALPABLE_RABBITS_FINISH);
        Task<String> palpableRabbitsFinishTask = Firebase.Database.add(Labor.class, palpableRabbitsFinish);

        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Labor setupNestLabor = new Labor();
        setupNestLabor.setDeadline(calendar.getTime());
        setupNestLabor.setPriority(Labor.Priority.LOW);
        setupNestLabor.setState(Labor.State.TO_DO);
        setupNestLabor.setManual(false);
        setupNestLabor.setTaskType(Labor.TaskType.SET_UP_NESTS);
        Task<String> setupNestTask = Firebase.Database.add(Labor.class, setupNestLabor);

        calendar.add(Calendar.DAY_OF_YEAR, 4);
        Labor labourLabor = new Labor();
        labourLabor.setDeadline(calendar.getTime());
        labourLabor.setPriority(Labor.Priority.LOW);
        labourLabor.setState(Labor.State.TO_DO);
        labourLabor.setManual(false);
        labourLabor.setTaskType(Labor.TaskType.LABOUR);
        Task<String> labourTask = Firebase.Database.add(Labor.class, labourLabor);

        calendar.add(Calendar.DAY_OF_YEAR, 25);
        Labor motherFeedStart = new Labor();
        motherFeedStart.setDeadline(calendar.getTime());
        motherFeedStart.setPriority(Labor.Priority.LOW);
        motherFeedStart.setState(Labor.State.TO_DO);
        motherFeedStart.setManual(false);
        motherFeedStart.setTaskType(Labor.TaskType.MOTHER_FEED_START);
        Task<String> motherFeedTask = Firebase.Database.add(Labor.class, motherFeedStart);

        calendar.add(Calendar.DAY_OF_YEAR, 27);
        Labor fodderFeedStart = new Labor();
        fodderFeedStart.setDeadline(calendar.getTime());
        fodderFeedStart.setPriority(Labor.Priority.LOW);
        fodderFeedStart.setState(Labor.State.TO_DO);
        fodderFeedStart.setManual(false);
        fodderFeedStart.setTaskType(Labor.TaskType.FODDER_FEED_START);
        Task<String> fodderFeedTask = Firebase.Database.add(Labor.class, fodderFeedStart);

        calendar.add(Calendar.DAY_OF_YEAR, 10);
        Labor retireFeedStart = new Labor();
        retireFeedStart.setDeadline(calendar.getTime());
        retireFeedStart.setPriority(Labor.Priority.LOW);
        retireFeedStart.setState(Labor.State.TO_DO);
        retireFeedStart.setManual(false);
        retireFeedStart.setTaskType(Labor.TaskType.RETIRE_FEED_START);
        Task<String> retireFeedTask = Firebase.Database.add(Labor.class, retireFeedStart);

        calendar.add(Calendar.DAY_OF_YEAR, 12);
        Labor saleLabor = new Labor();
        saleLabor.setDeadline(calendar.getTime());
        saleLabor.setPriority(Labor.Priority.LOW);
        saleLabor.setState(Labor.State.TO_DO);
        saleLabor.setManual(false);
        saleLabor.setTaskType(Labor.TaskType.SALE);
        Task<String> saleTask = Firebase.Database.add(Labor.class, saleLabor);

        Insemination newInsemination = new Insemination();
        newInsemination.setLabor(inseminationLabor.getId());
        newInsemination.setCycle(cycle.getId());
        Task<String> inseminationEventTask = Firebase.Database.add(Insemination.class, newInsemination);

        Palpation newPalpation = new Palpation();
        //newPalpation.setStartLabor(palpableRabbitsStart.getId());
        //newPalpation.setFinishLabor(palpableRabbitsFinish.getId());
        newPalpation.setCycle(cycle.getId());
        Task<String> palpationEventTask = Firebase.Database.add(Palpation.class, newPalpation);

        Labour newLabour = new Labour();
        newLabour.setLabor(labourLabor.getId());
        newLabour.setCycle(cycle.getId());
        Task<String> labourEventTask = Firebase.Database.add(Labour.class, newLabour);

        Sale newSale = new Sale();
        newSale.setLabor(saleLabor.getId());
        newSale.setCycle(cycle.getId());
        Task<String> saleEventTask = Firebase.Database.add(Sale.class, newSale);

        Task<String> cycleTask = Firebase.Database.add(Cycle.class, cycle);
        return Tasks.whenAll(photoperiodStartTask, photoperiodEndTask, inseminationTask,
                palpableRabbitsStartTask, palpableRabbitsFinishTask, setupNestTask, labourTask,
                motherFeedTask, fodderFeedTask, retireFeedTask, saleTask, inseminationEventTask,
                palpationEventTask, labourEventTask, saleEventTask);
    }

    public Task<Void> updateCycle(Cycle cycle)
    {
        return Firebase.Database.update(cycle, Cycle.class);
    }
}

package edu.ecu.cs.pirateplaces;

import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Test class for the {@link PiratePlaceViewModel}
 *
 * @author Mark Hills (mhills@cs.ecu.edu)
 * @version 1.0
 */
public class PiratePlaceViewModelTest
{
    private PirateBase mPirateBase;

    private PiratePlace mPiratePlace;

    private PiratePlaceViewModel mSubject;

    @Before
    public void setUp() throws Exception
    {
        mPirateBase = mock(PirateBase.class);
        mPiratePlace = new PiratePlace("Test Place", new Date());
        mSubject = new PiratePlaceViewModel(mPirateBase, new MockContext());
        mSubject.setPiratePlace(mPiratePlace);
    }

    @Test
    public void placeNameMatches()
    {
        assertThat(mSubject.getPlaceName(), is(mPiratePlace.getPlaceName()));
    }

    @Test
    public void lastVisitedDateMatches()
    {
        assertThat(mSubject.getLastVisitedDate(), is(mPiratePlace.getLastVisited()));
    }

    @Test
    public void updatingPlaceNameUpdatesDB()
    {
        mSubject.setPlaceName("New Test Place");
        verify(mPirateBase).updatePiratePlace(mSubject.getPiratePlace());
    }

    @Test
    public void updatingLastVisitedUpdatesDB()
    {
        mSubject.setLastVisitedDate(new Date());
        verify(mPirateBase).updatePiratePlace(mSubject.getPiratePlace());
    }
}
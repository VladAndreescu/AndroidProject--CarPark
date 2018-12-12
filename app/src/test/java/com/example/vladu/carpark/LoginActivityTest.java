package com.example.vladu.carpark;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class LoginActivityTest {

    @Mock
    private FirebaseAuth mockAuth;

    @Mock
    private Task<AuthResult> mockAuthTask;

    @Mock
    private Task<ProviderQueryResult> mockProviderQueryResultTask;

    @Mock
    private Task<Void> mockVoidTask;

    @Mock
    private AuthResult mockAuthResult;

    @Mock
    private ProviderQueryResult mockProviderQueryResult;

    @Mock
    private FirebaseUser mockUser;

    @Captor
    private ArgumentCaptor<OnCompleteListener> testOnCompleteListener;

    @Captor
    private ArgumentCaptor<OnSuccessListener> testOnSuccessListener;

    @Captor
    private ArgumentCaptor<OnFailureListener> testOnFailureListener;

    private Void mockRes = null;



    private DatabaseReference mockedDatabaseReference;


    @Before
    public void setUp() throws Exception {
        setupTask(mockAuthTask);
        setupTask(mockProviderQueryResultTask);
        setupTask(mockVoidTask);

        when(mockAuth.signInWithEmailAndPassword("email", "password")).thenReturn(mockAuthTask);
        when(mockAuth.createUserWithEmailAndPassword("email", "password")).thenReturn(mockAuthTask);

        when(mockAuth.getCurrentUser()).thenReturn(mockUser);

    }

    private <T> void setupTask(Task<T> task) {
        when(task.addOnCompleteListener(testOnCompleteListener.capture())).thenReturn(task);
        when(task.addOnSuccessListener(testOnSuccessListener.capture())).thenReturn(task);
        when(task.addOnFailureListener(testOnFailureListener.capture())).thenReturn(task);
    }

    @Test
    public void signInWithEmailAndPassword()  {
        final String email = "test@gmail.com";
        final String password = "test123";

        mockAuth.signInWithEmailAndPassword(email, password);
        verify(mockAuth).signInWithEmailAndPassword(eq("test@gmail.com"), eq("test123"));



    }

    @Test
    public void signInWithEmailAndPassword_AuthError()  {
        final String email = "test@gmail.com";
        final String password = "test123";

        mockAuth.signInWithEmailAndPassword("email", "password");

        Exception e = new Exception("something bad happened");

        verify(mockAuth).signInWithEmailAndPassword(eq("email"), eq("password"));

    }
}
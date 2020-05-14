package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {
  private TorpedoStore m1;
  private TorpedoStore m2;
  private GT4500 ship;

  @BeforeEach
  public void init(){
	  m1 = mock(TorpedoStore.class);
	  m2 = mock(TorpedoStore.class);
	    this.ship = new GT4500();
	    this.ship.injectDependencies(m1, m2, false);
  }

  @Test
  public void fireTorpedo_Single_Success(){
	// Arrange
	    when(m1.fire(1)).thenReturn(true);
	    when(m2.fire(1)).thenReturn(true);
	    // Act
	    boolean res = ship.fireTorpedo(FiringMode.SINGLE);

	    // Assert
	    assertEquals(true, res);
	    verify(m1, times(1)).fire(1);
	    verify(m2, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
	// Arrange
	    when(m1.fire(1)).thenReturn(true);
	    when(m2.fire(1)).thenReturn(true);
	    // Act
	    boolean res = ship.fireTorpedo(FiringMode.ALL);
	    // Assert
	    assertEquals(false, res);

  }

  @Test
  public void primaryStoreIsFired(){
    // Arrange
    when(m1.fire(1)).thenReturn(true);
    when(m2.isEmpty()).thenReturn(true);
 
    // Act
    boolean res1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean res2 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean res =res1 & res2;
    // Assert
    
    assertEquals(true, res);
   
    verify(m1, times(2)).fire(1);
    verify(m2, times(1)).isEmpty();
  
  }
  
  @Test
  public void fireTorpedo_AlternatingFire_Passed(){
    // Arrange
    when(m1.fire(1)).thenReturn(true);
    when(m2.fire(1)).thenReturn(true);
    // Act
    boolean res1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean res2 = ship.fireTorpedo(FiringMode.SINGLE);
    
    boolean res = res1 & res2;
    // Assert
    assertEquals(true, res);

    verify(m1, times(1)).fire(1);
    verify(m2, times(1)).fire(1);
  }
  
  @Test
  public void fireTorpedo_Single_First_Empty_Success(){
    // Arrange
    when(m1.isEmpty()).thenReturn(true);
    when(m2.fire(1)).thenReturn(true);
    // Act
    boolean res1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean res2 = ship.fireTorpedo(FiringMode.SINGLE);
    
    boolean res = res1 & res2;
    // Assert
    assertEquals(true, res);
    
    verify(m1, times(2)).isEmpty();
    verify(m2, times(2)).fire(1);
  }
  
  @Test
  public void reportSingleFailure(){
    // Arrange
    when(m1.fire(1)).thenReturn(false);
    when(m2.fire(1)).thenReturn(false);
    // Act
    boolean res = ship.fireTorpedo(FiringMode.SINGLE);;
    // Assert
    assertEquals(false, res);
    verify(m1, times(1)).fire(1);
    verify(m2, times(0)).fire(1);
  }
  
  @Test
  public void fireTorpedo_All_Failure(){
    // Arrange
    when(m1.fire(1)).thenReturn(false);
    when(m2.fire(1)).thenReturn(false);
    // Act
    boolean res = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, res);

  }
  
  
  
  
  @Test
  public void fireLazerSingle(){
    // Arrange
    when(m1.isEmpty()).thenReturn(true);

    // Act
    boolean res = ship.fireLaser(FiringMode.SINGLE);
    // Assert
    assertEquals(true, res);
    verify(m1, times(0)).fire(1);

  }
  @Test
  public void fireLazerALL(){
    // Arrange
    when(m1.fire(1)).thenReturn(true);
    when(m2.fire(1)).thenReturn(true);

    // Act
    boolean res = ship.fireLaser(FiringMode.ALL);
    // Assert
    assertEquals(false, res);
    verify(m1, times(0)).fire(1);
    verify(m2, times(0)).fire(1);

  }
  
}

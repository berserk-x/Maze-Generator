import java.awt.Color;

public class maze extends mazebase
{
    // default constructor suffices and is equivalent to
    // public maze() { super(); }

 @Override
 public void digout(int y, int x)   // modify this function
 {
     // The following is a skeleton program that demonstrates the mechanics
     // needed for the completion of the program.

     // We always dig out two spaces at a time: we look two spaces ahead
     // in the direction we're trying to dig out, and if that space has
     // not already been dug out, we dig out that space as well as the
     // intermediate space.  This makes sure that there's always a wall
     // separating adjacent corridors.

     M[y][x] = 1;  // digout maze at coordinate y,x
     drawblock(y,x);  // change graphical display to reflect space dug out


     int[] p = {0, 1, 2, 3}; // 0 - 3 N E S W
     for (int i = 0; i < 3; i++) {
        int r = (int)(Math.random()*(4-i)) + i;
        int temp = p[i];
        p[i] = p[r];
        p[r] = temp;
     }

     
     int[] DX = {0, 1, 0, -1};
     int[] DY = {-1, 0, 1, 0};

     for (int i=0; i < 4; i++) {
        int d = p[i];
        int nx = x + DX[d] * 2;
        int ny = y + DY[d] * 2;
        if ((nx>= 0 && nx<mwidth && ny >=0 && ny < mheight && M[ny][nx] == 0)) {
            M[y+DY[d]][x+DX[d]] = 1;
            drawblock(y+DY[d], x+DX[d]);
            digout(ny, nx);
        }

     }
     
 }//digout
record coord(int y, int x) {}
protected coord[][] PATH;

    @Override
    public void solve()
    {
        int targety = (mheight - 2);
        int targetx = (mwidth -1);
        M[targety][targetx] = 1; 
        drawblock(targety,targetx); 
        int y = 1;
        int x = 1;
        drawdot(y, x);
        nextframe(30);
        int[] DX = {0, 1, 0, -1};
        int[] DY = {-1, 0, 1, 0};

        PATH = new coord[mheight][mwidth];  
            

        while (!(y == mheight-2 && x == mwidth-1)) {
            int best = 0x7fffffff;
            int dir = 0;
            for (int d = 0; d < 4; d++) {
                int ny = y+DY[d];
                int nx = x+DX[d];
                if (ny >= 0 && ny < mheight && nx >= 0 && nx < mwidth) {
                    if (M[ny][nx] != 0) {
                        if (M[ny][nx] < best) {
                            best = M[ny][nx];
                            dir = d;
                        }
                    }
                    
                }
            }
            if (PATH[y + DY[dir]][x + DX[dir]] == null) {
            PATH[y + DY[dir]][x + DX[dir]] = new coord(y, x);  
        }
            drawblock(y,x);
            M[y][x]++;
            x = x+DX[dir];
            y = y+DY[dir];
            drawdot(y,x);
            nextframe(30);
        }
        trace(targety, targetx);
    }
    public void trace(int y, int x) {
        while(y != 1 || x != 1) {
            drawdot(y, x);
            nextframe(30);
            coord previous = PATH[y][x];
            y = previous.y();
            x = previous.x();
        }

        drawdot(1, 1);
        nextframe(30);
        
    }
    

    public static void main(String[] av)
    {
	new maze(); // constructor of superclass will initiate everything
    }

    // other hints:  override customize to change maze parameters:
    @Override
    public void customize()
    {
        wallcolor = Color.black;
        pathcolor = Color.blue;
        dotcolor = Color.yellow;
	// ... can change mwidth, mheight, bw,bh, colors here
    }

}//maze subclass

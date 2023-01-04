
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;




class BoardGame {
    public  char[][] id;
    public  int[] indexCnt ;
    public  WeightedQuickUnionUF uf;
    public BoardGame(int h, int w){
        int N = h * w;
        int idx =0 ;
        id = new char[h][w];
        indexCnt = new int[id.length * id[0].length];
        uf= new WeightedQuickUnionUF(id.length * id[0].length);
        for(int i = 0 ; i < h; i++){
            for(int j = 0; j < w; j++){
                id[i][j] = '*';
            }
        }
        //set every indexCnt = 0
        for(int i = 0 ; i < indexCnt.length; i++){
            indexCnt[i] = 0;
        }
//        System.out.println(Arrays.deepToString(id));

    } // create a board of size h*w
    public void putStone(int[] x, int[] y, char stoneType) {
        //declare variable
        int index = 0;
        //input & union

        for(int i = 0 ; i < x.length; i++){
            int idx;

            if(x[i] == 0){
                    idx = (x[i]*(id[0].length-1) + y[i]);
                }else {
                    idx = (x[i]*(id[0].length) + y[i]);
                }
            id[x[i]][y[i]] = stoneType;
            int xaxis = x[i];
            int yaxis = y[i];
            int up = x[i]-1;
            int down =x[i]+1;
            int left = y[i]-1;
            int right = y[i]+1;
            int value = indexCnt[uf.find(idx)];

            indexCnt[uf.find(idx)] +=4;

            if(left<0 || down>= id.length || up<0 || right>= id[0].length){
                //leftup
                if(left<0 && up<0 && right<id[0].length && down<id.length && right>=0 && down>=0){
                    //right
                    if(id[xaxis][right] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx+1)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx+1)];
                            uf.union(idx, idx +1);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx+1] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }
                    //down
                    if(id[down][yaxis] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx+id[0].length)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx+id[0].length)];
                            uf.union(idx, idx +id[0].length);
                            indexCnt[uf.find((idx))] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx+id[0].length] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }
                    //X
                    if(id[xaxis][right] != id[xaxis][yaxis] && id[xaxis][right] !='*' ){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx + 1)] -=1 ;
                    }
                    if(id[down][yaxis] != id[xaxis][yaxis] && id[down][yaxis] !='*'){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx + id[0].length)] -=1 ;
                    }
                //rightup
                } else if (right >=id[0].length && up <0 && left<id[0].length && down<id.length && left>=0 && down>=0) {
                    //down
                    if(id[down][yaxis] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx+id[0].length)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx+id[0].length)];
                            uf.union(idx, idx +id[0].length);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;

                            if(uf.find(idx) == idx){
                                indexCnt[idx+id[0].length] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }
                    //left
                    if(id[xaxis][left] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx-1)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx-1)];
                            uf.union(idx, idx -1);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//
                            if(uf.find(idx) == idx){
                                indexCnt[idx-1] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }

                    //X
                    if(id[down][yaxis] != id[xaxis][yaxis] && id[down][yaxis] != '*'){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx + id[0].length)] -=1 ;
                    }
                    if(id[xaxis][left] != id[xaxis][yaxis] && id[xaxis][left] !='*'){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx - 1)] -=1 ;
                    }
                //leftdown
                } else if (left<0 && down>=id.length && right<id[0].length && up<id.length && right>=0 && up>=0) {
                    //right
                    if(id[xaxis][right] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx+1)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx+1)];
                            uf.union(idx, idx +1);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx+1] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }
                    //up
                    if(id[up][yaxis] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx-id[0].length)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx-id[0].length)];
                            uf.union(idx, idx -id[0].length);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx-id[0].length] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }

                    //X
                    if(id[up][yaxis] != id[xaxis][yaxis] && id[up][yaxis] != '*'){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx - id[0].length)] -=1 ;
                    }
                    if(id[xaxis][right] != id[xaxis][yaxis] && id[xaxis][right] != '*'){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx + 1)] -=1 ;
                    }

                //rightdown
                } else if (right>=id[0].length && down>=id.length && left<id[0].length && up<id.length && left>=0 && up>=0) {
                    //up
                    if(id[up][yaxis] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx-id[0].length)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx-id[0].length)];
                            uf.union(idx, idx -id[0].length);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx-id[0].length] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }
                    //left
                    if(id[xaxis][left] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx-1)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx-1)];
                            uf.union(idx, idx -1);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx-1] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }
                    //X
                    if(id[up][yaxis] != id[xaxis][yaxis] && id[up][yaxis] !='*'){
                        indexCnt[uf.find(idx)] -=1;
                    }
                    if(id[xaxis][left] != id[xaxis][yaxis] && id[xaxis][left] != '*'){
                        indexCnt[uf.find(idx)] -=1;
                    }
                //left
                } else if (left < 0 && right<id[0].length && up<id.length && down<id.length && right>=0 && up>=0 && down>=0) {
                    //up
                    if(id[up][yaxis] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx-id[0].length)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx-id[0].length)];
                            uf.union(idx, idx -id[0].length);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx-id[0].length] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }
                    //right
                    if(id[xaxis][right] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx+1)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx+1)];
                            uf.union(idx, idx +1);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx+1] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }
                    //down
                    if(id[down][yaxis] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx+id[0].length)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx+id[0].length)];
                            uf.union(idx, idx +id[0].length);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx+id[0].length] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }

                    //X
                    if(id[up][yaxis] != id[xaxis][yaxis] && id[up][yaxis] != '*'){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx - id[0].length)] -=1 ;
                    }
                    if(id[xaxis][right] != id[xaxis][yaxis] && id[xaxis][right] != '*'){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx + 1)] -=1 ;
                    }
                    if(id[down][yaxis] != id[xaxis][yaxis] && id[down][yaxis] != '*'){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx + id[0].length)] -=1 ;
                    }
                //right
                } else if (right >= id[0].length && left<id[0].length && up<id.length && down<id.length && left>=0 && up>=0 && down>=0) {
                    //up
                    if(id[up][yaxis] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx-id[0].length)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx-id[0].length)];
                            uf.union(idx, idx -id[0].length);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx-id[0].length] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }
                    //down
                    if(id[down][yaxis] == id[xaxis][yaxis] ){
                        if(uf.find(idx) == uf.find(idx+id[0].length)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx+id[0].length)];
                            uf.union(idx, idx +id[0].length);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx+id[0].length] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }
                    //left
                    if(id[xaxis][left] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx-1)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx-1)];
                            uf.union(idx, idx -1);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx-1] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }
                    //X
                    if(id[up][yaxis] != id[xaxis][yaxis] && id[up][yaxis] != '*'){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx - id[0].length)] -=1 ;
                    }
                    if(id[down][yaxis] != id[xaxis][yaxis] && id[down][yaxis] != '*'){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx + id[0].length)] -=1 ;
                    }
                    if(id[xaxis][left] != id[xaxis][yaxis] && id[xaxis][left] != '*'){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx - 1)] -=1 ;
                    }
                //up
                } else if (up < 0 && down<id.length && right<id[0].length && left<id[0].length && down>=0 && right>=0 && left>=0) {
                    //right
                    if(id[xaxis][right] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx+1)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx+1)];
                            uf.union(idx, idx +1);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx+1] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }
                    //down
                    if(id[down][yaxis] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx+id[0].length)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx+id[0].length)];
                            uf.union(idx, idx +id[0].length);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx+id[0].length] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }
                    //left
                    if(id[xaxis][left] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx-1)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx-1)];
                            uf.union(idx, idx -1);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx-1] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }
                    //X
                    if(id[xaxis][right] != id[xaxis][yaxis] && id[xaxis][right] != '*'){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx + 1)] -=1 ;
                    }
                    if(id[down][yaxis] != id[xaxis][yaxis] && id[down][yaxis] != '*'){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx + id[0].length)] -=1 ;
                    }
                    if(id[xaxis][left] != id[xaxis][yaxis] && id[xaxis][left] != '*'){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx - 1)] -=1 ;
                    }
                    //down
                } else if (down >= id.length && up<id.length && right<id[0].length && left<id[0].length && up>=0 && right>=0 && left>=0) {
                    //up
                    if(id[up][yaxis] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx-id[0].length)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx-id[0].length)];
                            uf.union(idx, idx -id[0].length);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx-id[0].length] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }
                    //right
                    if(id[xaxis][right] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx+1)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx+1)];
                            uf.union(idx, idx +1);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx+1] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }
                    //left
                    if(id[xaxis][left] == id[xaxis][yaxis]){
                        if(uf.find(idx) == uf.find(idx-1)){
                            indexCnt[uf.find(idx)] -=2;
                        }
                        else{
                            int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx-1)];
                            uf.union(idx, idx -1);
                            indexCnt[uf.find(idx)] = temp;
                            indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                            if(uf.find(idx) == idx){
                                indexCnt[idx-1] = 0;
                            }
                            else{
                                indexCnt[idx] = 0;
                            }
                        }
                    }
                    //X
                    if(id[up][yaxis] != id[xaxis][yaxis] && id[up][yaxis] != '*'){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx - id[0].length)] -=1 ;
                    }
                    if(id[xaxis][right] != id[xaxis][yaxis] && id[xaxis][right] !='*'){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx + 1)] -=1 ;
                    }
                    if(id[xaxis][left] != id[xaxis][yaxis] && id[xaxis][left] != '*'){
                        indexCnt[uf.find(idx)] -=1;
                        indexCnt[uf.find(idx - 1)] -=1 ;
                    }

                }
                else if(left <0 && up<0 && right>id[0].length && down>id.length){
                    break;
                }

            } else {
                //up
                if(id[up][yaxis] == id[xaxis][yaxis]){
                    if(uf.find(idx) == uf.find(idx-id[0].length)){
                        indexCnt[uf.find(idx)] -=2;
                    }
                    else{
                        int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx-id[0].length)];
                        uf.union(idx, idx -id[0].length);
//                        indexCnt[uf.find(idx)] = (indexCnt[idx-id[0].length] < indexCnt[uf.find(idx-id[0].length)]) ? indexCnt[idx-id[0].length] : indexCnt[uf.find(idx-id[0].length)];
                        indexCnt[uf.find(idx)] = temp;
                        indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                        if(uf.find(idx) == idx){
                            indexCnt[idx-id[0].length] = 0;
                        }
                        else{
                            indexCnt[idx] = 0;
                        }
                    }
                }
                //right
                if(id[xaxis][right] == id[xaxis][yaxis]){
                    if(uf.find(idx) == uf.find(idx+1)){
                        indexCnt[uf.find(idx)] -=2;
                    }
                    else{
                        int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx+1)];
                        uf.union(idx, idx +1);
                        indexCnt[uf.find(idx)] = temp;
                        indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                        if(uf.find(idx) == idx){
                            indexCnt[idx+1] = 0;
                        }
                        else{
                            indexCnt[idx] = 0;
                        }
                    }
                }
                //down
                if(id[down][yaxis] == id[xaxis][yaxis]){
                    if(uf.find(idx) == uf.find(idx+id[0].length)){
                        indexCnt[uf.find(idx)] -=2;
                    }
                    else{
                        int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx+id[0].length)];
                        uf.union(idx, idx +id[0].length);
                        indexCnt[uf.find(idx)] = temp;
                        indexCnt[uf.find(idx)] -=2;
//                                indexCnt[idx] = 0;
                        if(uf.find(idx) == idx){
                            indexCnt[idx+id[0].length] = 0;
                        }
                        else{
                            indexCnt[idx] = 0;
                        }
                    }
                }
                //left
                if(id[xaxis][left] == id[xaxis][yaxis]){
                    if(uf.find(idx) == uf.find(idx-1)){
                        indexCnt[uf.find(idx)] -=2;
                    }
                    else{
                        int temp = indexCnt[uf.find(idx)] + indexCnt[uf.find(idx-1)];
                        uf.union(idx, idx -1);
//                        System.out.println("uffind_"+(idx-1)+": "+uf.find(idx-1));
//                        indexCnt[uf.find(idx)] = (indexCnt[idx-1] < indexCnt[uf.find(idx-1)]) ? indexCnt[idx-1] : indexCnt[uf.find(idx-1)];
                        indexCnt[uf.find(idx)] = temp;
                        indexCnt[uf.find(idx)] -=2;
                        if(uf.find(idx) == idx){
                            indexCnt[idx-1] = 0;
                        }
                        else{
                            indexCnt[idx] = 0;
                        }
                    }
                }
                //up X
                if(id[up][yaxis] != id[xaxis][yaxis] && id[up][yaxis] != '*'){
                    indexCnt[uf.find(idx)] -=1;
                    indexCnt[uf.find(idx - id[0].length)] -=1 ;
                }
                //right X
                if(id[xaxis][right] != id[xaxis][yaxis] && id[xaxis][right] !='*'){
                    indexCnt[uf.find(idx)] -=1;
                    indexCnt[uf.find(idx + 1)] -=1 ;
                }
                //down X
                if(id[down][yaxis] != id[xaxis][yaxis] && id[down][yaxis] !=  '*'){
                    indexCnt[uf.find(idx)] -=1;
                    indexCnt[uf.find(idx + id[0].length)] -=1 ;
                }
                //left X
                if(id[xaxis][left] != id[xaxis][yaxis] && id[xaxis][left] != '*'){
                    indexCnt[uf.find(idx)] -=1;
                    indexCnt[uf.find(idx - 1)] -=1 ;
                }
            }



//            value = indexCnt[uf.find(idx)];





                }

//        for(int i = 0 ; i < (id.length * id[0].length) ; i++){
//            System.out.println(i+": "+indexCnt[i]);
//        }



//        System.out.println(Arrays.deepToString(id));
    }// put stones of the specified type on the board according to the coordinates
    public boolean surrounded(int x, int y) {


        /****************************************/

//        for(int i = 0 ; i < indexCnt.length; i++){
//            System.out.println(i+": "+indexCnt[i]);
//        }

        /****************************************/
        int idx;
        if(x == 0){
            idx = (x*(id[0].length-1) + y);
        }else {
            idx = (x*(id[0].length) + y);
        }
        return (indexCnt[uf.find(idx)] == 0) ? true : false;
    }
    // Answer if the stone and its connected stones are surrounded by another type of stones
    public char getStoneType(int x, int y){
        return id[x][y];
    } // Get the type of the stone at (x,y)
    public int countConnectedRegions(){
        int str = 0;
        for(int i = 0 ; i < id.length; i++){
            for(int j = 0 ; j < id[0].length; j++){
                if(id[i][j] == '*'){
                    str++;
                }
            }
        }

        return  uf.count()-str;

    } // Get the number of connected regions in the board, including both types of the stones





    public static void main(String args[]){

//        test(args);

    }
}


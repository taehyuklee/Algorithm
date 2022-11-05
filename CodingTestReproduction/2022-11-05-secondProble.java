import java.util.*;

class Char{
    int serverNum;
    int index;
    String name;

    public Char(int serverNum, int index, String name){
        this.serverNum = serverNum;
        this.index = index;
        this.name = name;
    }

    @Override
    public String toString(){
        return "serverNum: " + this.serverNum + ", " + "index: " + this.index + ", " + "charName: " + this.name + " | "; 
    }

    public int getServerNum(){
        return this.serverNum;
    }

    public String getName(){
        return this.name;
    }

    public int getIndex(){
        return this.index;
    }
}

class Solution {


    public String[] solution(int n, String[] record) {
        String[] answer = {};

        List<Char> listChar = new ArrayList<Char>();
        for(int i=0; i<record.length; i++){
            String temp = record[i];   
            String[] tempArray= temp.split(" ");

            //from record
            int serverNum =Integer.parseInt(tempArray[0]);
            String charNm = tempArray[1];

            List<Char> tempServer = findByServer(listChar, serverNum);

            //이름이 중복되는지 확인 
            List<Char> duplicated = findByServerAndName(tempServer, serverNum, charNm);
            if(duplicated.size() == 0){

                // System.out.println(tempServer);

                //갯수가 넘었는지 확인 
                    //int cnt = countServerChar(tempServer, serverNum);
                    int cnt =tempServer.size();
                    //System.out.println(cnt);

                    if(cnt>=5){
                    //넘었다면 가장 오래된거 찾기
                    List<Char> oldestList = OrderByIndex(tempServer, serverNum);
                   // System.out.println(oldestList.get(0).name);

                    //listChar에서 위에서 찾은 oldestList.get(0)을 제거해줘야 한다. 
                    deleteByName(listChar, oldestList.get(0).name, serverNum);  

                    //제거하고 가장 최신 index +1해주기 
                    listChar.add(new Char(serverNum, i+1, charNm));

                    }else{
                        //안넘었으면 그냥 넣어주기 
                        listChar.add(new Char(serverNum, i+1, charNm));
                    }
            }else{
                //중복이 있을 경우 
                //System.out.println(duplicated.get(0).getName());
                continue;
            }


        }

        // System.out.println(listChar);
        // System.out.println();

        answer = new String[listChar.size()];
        Collections.sort(listChar, Comparator.comparing(Char::getServerNum).thenComparing(Char::getIndex));
        for(int i=0; i<listChar.size(); i++){
            answer[i] = listChar.get(i).name;
        }

        return answer;
    }


    public void deleteByName(List<Char> listChar, String name, int serverNum){

        for(int i=0; i<listChar.size(); i++){
            if(serverNum == listChar.get(i).serverNum && name.equals(listChar.get(i).name)){
                listChar.remove(i);
            }
        }

        return;
    }

    public List<Char> OrderByIndex(List<Char> listChar, int serverNum){
        List<Char> tempList = new ArrayList<>();
        for(int i=0; i<listChar.size(); i++){
            if(serverNum == listChar.get(i).serverNum){
                tempList.add(listChar.get(i));
            }
        }

        Collections.sort(tempList, Comparator.comparing(Char::getIndex));
        return tempList;
    }

    public List<Char> findByServer(List<Char> listChar, int serverNum){
        //deepCopy할 필요가 없다. 새로운 자료구조에 담아서만 주자
        List<Char> tempList = new ArrayList<>();
        for(int i=0; i<listChar.size(); i++){
            if(serverNum == listChar.get(i).serverNum){
                tempList.add(listChar.get(i));
            }
        }
        return tempList;
    }

    public List<Char> findByServerAndName(List<Char> listChar, int serverNum, String name){
        //deepCopy할 필요가 없다. 새로운 자료구조에 담아서만 주자
        List<Char> tempList = new ArrayList<>();
        for(int i=0; i<listChar.size(); i++){
            if(serverNum == listChar.get(i).serverNum && name.equals(listChar.get(i).name)){
                tempList.add(listChar.get(i));
            }
        }
        return tempList;
    }

    public int countServerChar(List<Char> listChar, int serverNum){
        int count=0;
        for(int i=0; i<listChar.size(); i++){
            if(serverNum == listChar.get(i).serverNum){
                count +=1;
            }
        }
        return count;
    }

}

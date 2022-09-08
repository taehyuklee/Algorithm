import java.util.*;
import java.util.Map.Entry;

class Music{
    
    public String genre;
    public Integer play;
    public Integer uniqueNum;
    
    public Music(String genre, Integer play, Integer uniqueNum){
        this.genre = genre;
        this.play = play;
        this.uniqueNum = uniqueNum;
    }
    
    public Integer getPlay(){
        return this.play;
    }
    
    public Integer getUniqueNum(){
        return this.uniqueNum;
    }
    
    @Override
    public String toString(){
        return "genre:" + genre + " " +"paly:" + play + " "  + "uniqueNum:" + uniqueNum + "\n";
    }
    
}

class PlayComparator implements Comparator<Music>{
    @Override
    public int compare(Music m1, Music m2){
        if(m1.play < m2.play){
            return -1;
        }else if(m1.play > m2.play){
            return 1;
        }
        return 0;
    }
}

class NumComparator implements Comparator<Music>{
    @Override
    public int compare(Music m1, Music m2){
        if(m1.uniqueNum < m2.uniqueNum){
            return -1;
        }else if(m1.uniqueNum > m2.uniqueNum){
            return 1;
        }
        return 0;
    }
}



class Solution {
    
    public Map<String, Integer> makeHashMap(String[] genres, int[] plays){
        
        Map<String, Integer> hashMap = new HashMap<String, Integer>();
        
        for(int i=0; i<genres.length; i++){
            if(!hashMap.containsKey(genres[i])){
                hashMap.put(genres[i], plays[i]);
            }else{
                hashMap.put(genres[i], hashMap.get(genres[i])+plays[i]);
            }
        }
        
        return hashMap;
    }
    
    public List<String> sortHashMap(Map<String, Integer> map){
        //Set<String> keys = map.keySet();
        List<Integer> valuesList  = new ArrayList<Integer>(map.values());
        List<String> keysList  = new ArrayList<String>();
        
        Collections.sort(valuesList, Collections.reverseOrder());
        
        for(Integer value: valuesList){
            for(Entry<String, Integer> entry: map.entrySet()){
                if( value == entry.getValue()){
                    keysList.add(entry.getKey());
                }
            }
        }
        
        return keysList;
    }

    
    
    public int[] solution(String[] genres, int[] plays) {
        //int[] answer = {};
        List<Integer> answerList = new ArrayList<Integer>();
        
        //속한 노래가 많이 재생된 장르 -> 장르 내에서 많이 재생된 노래 -> 장르 내에서 재생횟수 같으면 고유번호 낮은 노래
        
        System.out.println(Arrays.toString(genres));
        
        //HashMap으로 각 장르별로 재생횟수 더하기
        Map<String, Integer> hashMap = makeHashMap(genres, plays);
        System.out.println(hashMap);
        
        //HashMap의 value기준으로 Sort하기
        List<String> keysSortedList = sortHashMap(hashMap);
        System.out.println(keysSortedList);
        
        //장르와 고유번호 (너무 Pythonic하다)
        // List<List<String, Integer, Integer>> list = new ArrayList<>();
        // for (int i=0; i<genres.length; i++){
        //     List<String, Integer, Integer> element = new ArrayList<>(genres[i], i, plays[i]);
        //     list.add(element);
        // }
        
        //OOP스럽게 해보자
        List<Music> musicList = new ArrayList<Music>();
        for (int i=0; i<genres.length; i++){
            Music music = new Music(genres[i],plays[i],i);
            musicList.add(music);
        }
        
        //System.out.println(musicList.toString());

        //재생횟수에 따라 장르별로 나열하기
        List<Music> selectedList = new ArrayList<Music>();
        System.out.println("\n");
        for(String list : keysSortedList){
            for(int i=0; i<musicList.size(); i++){
                
                if(list.equals(musicList.get(i).genre)){
                    selectedList.add(musicList.get(i));
                }
                
            }
            //골라낸것중에 플레이 List로
            //Comparator<Music> reversed = Comparator.comparing(Music::getPlay).reversed();
            selectedList.sort(Comparator.comparing(Music::getPlay).reversed().thenComparing(Music::getUniqueNum));
            
            //Collections.sort(selectedList, Comparator.comparing(reverse));

            System.out.println(selectedList);
            if(selectedList.size() ==1){
                    answerList.add(selectedList.get(0).getUniqueNum());
            }else{
                for(int j=0; j<2; j++){
                    answerList.add(selectedList.get(j).getUniqueNum());
                }
            }
            
            //초기화
            selectedList= new ArrayList<Music>();
        }
        System.out.println(answerList);
        
        
        //변환
        int[] answer = new int[answerList.size()];
        for(int i=0; i<answerList.size(); i++){
            answer[i] = answerList.get(i);
        }

        return answer;
    }
}

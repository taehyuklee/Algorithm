class music_cls:
    
    def __init__ (self, idx, genre, tot_play, in_play):
        self.idx = idx
        self.genre = genre
        self.tot_play = tot_play
        self.in_play = in_play

    def __repr__ (self):
        return f"고유번호: {self.idx}, 장르: {self.genre}, 전체 재생 횟수: {self.tot_play}, 장르내 재생 횟수: {self.in_play} \n"
    
    
def solution(genres, plays):
    
    answer =[]
    
    python_map = {}
    
    # O(N)
    for genre, play in zip(genres, plays):

        if genre not in python_map.keys():
            python_map[genre] = play 
        else:
            python_map[genre] += play
    
    print(python_map)
    
    music_list = []
    
    # O(N)
    for idx in range(len(genres)):
        genre = genres[idx]
        play = plays[idx]
        tot_play = python_map[genre]
        
        music = music_cls(idx, genre, tot_play, play)
        music_list.append(music)
        
    print(music_list)
    
    # O(NlogN)
    music_list.sort(key=lambda music: (-music.tot_play, -music.in_play, music.idx))
    
    print(music_list)
    
    # O(N)
    genre_counter = {}  # 장르별 선택 개수 기록
    
    # O(N) - 최대 2곡씩 뽑기 (마지막에 장르 Counter를 map으로 관리한다는 생각)
    for music in music_list:
        cnt = genre_counter.get(music.genre, 0)
        if cnt < 2:
            answer.append(music.idx)
            genre_counter[music.genre] = cnt + 1
    
    print(genre_counter)
    
    return answer

class Music:
    
    def __init__(self, index, play_num, genre, tot_num):
        self.index = index
        self.play_num = play_num
        self.genre = genre
        self.tot_num = tot_num
    
    def __repr__(self):
        return "index: " + str(self.index) + ", play_num: " + str(self.play_num) + ", genre: " + self.genre + ", total_play: " + str(self.tot_num)

def solution(genres, plays):
    answer = []
    music_dict = {}
    music_list = []
    print(genres)
    print(plays)
    
    for i in range(len(genres)):
        if genres[i] in music_dict.keys():
            music_dict[genres[i]] += plays[i]
        else:
            music_dict[genres[i]] = plays[i]
    
    for i in range(len(genres)):
        music = Music(i, plays[i], genres[i], music_dict[genres[i]])
        music_list.append(music)
    
    music_list.sort(key=lambda music: (-music.tot_num, -music.play_num, music.index) )
    
    old_genre = music_list[0].genre
    pick_num = 1
    answer.append(music_list[0].index)
    
    for i in range(1, len(music_list)):
        # 숫자 카운트 1개 2개가 나왔으면 막아야함 <3으로 하면 2개 나오고 한 번 더하게 됨
        if old_genre == music_list[i].genre and pick_num <2:
            answer.append(music_list[i].index)
            pick_num += 1
        elif old_genre != music_list[i].genre:
            answer.append(music_list[i].index)
            pick_num = 1
            old_genre = music_list[i].genre
        else:
            continue

    return answer

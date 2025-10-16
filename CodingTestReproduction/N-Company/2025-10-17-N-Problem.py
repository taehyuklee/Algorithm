# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

'''
Algorithm Cases

goodjob (5)
g -> o -> o -> d -> j -> o -> b
6 (remove) -> 14 (remove) -> 14 (remove) -> 3 (remove) -> 9 (remove) -> 14 (remove)-> 1

banana (3)
b -> a -> n -> a -> n -> a
1
1 (remove) -> 0
        0  -> 13 (remove) -> 0 -> 13 (remove)-> 0

trust (2)
t -> r -> u -> s -> t (2)
19 (remove) -> 17 -> 20 (remove) -> 18 -> 19

apple : 한 번에 두 개 제거하는 케이스도 존재해야 함.
a -> p -> p -> l -> e (3)
0 -> 15 (remove) -> 15 (remove)-> 11 (remove) -> 4
0 1 2 3

shotgun: 5
s -> h -> o -> t -> g -> u -> n
18 (remove) -> 7 (remove) -> 14 (remove)-> 19 (remove) -> 6 -> 20 (remove) -> 13
'''


def solution(S):
    # Implement your solution here
    answer = 0

    alphabet_dict = {'a': 0, 'b': 1, 'c': 2, 'd': 3, 'e': 4, 'f': 5, 'g': 6, 'h': 7, 'i': 8, 'j': 9, 'k': 10, 'l': 11,
                     'm': 12, 'n': 13, 'o': 14,
                     'p': 15, 'q': 16, 'r': 17, 's': 18, 't': 19, 'u': 20, 'v': 21, 'w': 22, 'x': 23, 'y': 24, 'z': 25}

    old_word = S[0]
    old_order = 0
    old_word_num = alphabet_dict[old_word]

    # num 사전 순서 ref는 string의 순서
    ref_num = old_word_num
    ref_order = 0

    # banana 예시
    # shotgun case 맨 앞까지 다 삭제되는 경우.
    # apple
    # 01234
    # str_list = list(S)
    # print(str_list)

    for new_order in range(1, len(S)):
        new_word = S[new_order]
        new_word_num = alphabet_dict[new_word]
        old_word_num = alphabet_dict[old_word]

        # print(old_word, old_word_num)
        # print(new_word , new_word_num)
        # print()

        if old_word_num <= new_word_num:
            old_word = new_word
            old_order = new_order
            if ref_num == new_word_num:
                ref_order = new_order

            # print(ref_order)
            # print("gogo")
            # print("-------------- next ------------------")
            continue
        else:
            # 제거해야함.
            # reference되는 단어사전까지 지워야 할 경우
            if ref_num > new_word_num:
                # print("new_word_num < ref_num")
                # print(new_order)
                # print(old_order)
                # print(ref_order)
                answer += (new_order - ref_order)
                ref_num = new_word_num

                old_word = new_word
                # ref_word = new_word
                old_order = new_order
                ref_order = new_order

                # print(answer)
                # print("-------------- next ------------------")
                continue

            # reference와 new word사이의 단어만 지워야 할 경우
            else:
                # print("new_word_num >= ref_num")
                # print(new_order)
                # print(old_order)
                # print(ref_order)
                answer += (new_order - ref_order) - 1  # 자기 자신도 빼야함.
                'apple'
                '0 15 15 11 4'

                'banana'

                if new_word_num == ref_num:
                    ref_order = new_order
                    ref_num = alphabet_dict[S[ref_order]]
                elif old_word == S[old_order - 1]:
                    ref_order = new_order
                    ref_num = alphabet_dict[S[ref_order]]

                # if old_word == S[old_order-1]:
                #     'apple'
                #     ref_order = old_order
                #     ref_num = alphabet_dict[S[ref_order]]
                # elif new_word_num == ref_num:
                #     ref_order = new_order
                # else:
                #     'banana'
                #     ref_order = new_order -1
                #     ref_num = alphabet_dict[S[ref_order]]

                old_word = new_word
                old_order = new_order

                # print()
                # print("answer")
                # print(answer)
                # print("-------------- next ------------------")

    return answer


def solution(S):
    alphabet_dict = {ch: i for i, ch in enumerate('abcdefghijklmnopqrstuvwxyz')}
    arr = [alphabet_dict[ch] for ch in S]

    # Longest Increasing Subsequence (LIS)
    import bisect
    lis = []
    for x in arr:
        pos = bisect.bisect_left(lis, x)
        if pos == len(lis):
            lis.append(x)
        else:
            lis[pos] = x

    # 최소 제거 개수 = 전체 길이 - LIS 길이
    return len(S) - len(lis)

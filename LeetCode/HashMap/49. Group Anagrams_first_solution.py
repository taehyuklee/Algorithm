class Solution:

    def groupAnagrams(self, strs: List[str]) -> List[List[str]]:
        answer_list = []
        visit_list = [False] * len(strs)
        # print(visit_list)

        def decompse_str(str_element) -> Dict[str, int]:

            str_count = {}

            for ch in str_element:
                if str_count.get(ch):
                    str_count[ch] += 1
                else:
                    str_count[ch] = 1

            return str_count

        def compare_anagram(source_hash, target_hash) -> bool:

            # 길이가 다르면 일단 False
            if len(source_hash) != len(target_hash):
                return False

            # 모든 요소가 value가 같은지 확인
            for key, value in source_hash.items():
                if value != target_hash.get(key):
                    return False
                else:
                    continue

            return True

        # main 비교 로직
        for i in range (len(strs)):
            group_list = []
            if visit_list[i] != True:
                source_hash = decompse_str(strs[i])
                group_list.append(strs[i])
            else:
                continue

            for j in range(i+1, len(strs)):
                target_hash = decompse_str(strs[j])
                if compare_anagram(source_hash, target_hash):
                    group_list.append(strs[j])
                    visit_list[j] = True

            answer_list.append(group_list)

        return answer_list

        # test code
        # g = {"a":1, "b":2}
        # g1 = {"a":1, "b":2}
        # print(compare_anagram(g, g1))

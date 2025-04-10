final_answer = []

from copy import deepcopy


def travle(port_dict, visit_dict, src, answer, cur_depth, tot_depth):
    global final_answer
    
    if final_answer:
        return

    if cur_depth == tot_depth:
        final_answer = deepcopy(answer)
        return

    if src in port_dict.keys():
        dest_list = port_dict[src]
        visit_list = visit_dict[src]
    else:
        return

    for idx in range(len(dest_list)):
        if not visit_list[idx]:
            dest = dest_list[idx]
            visit_list[idx] = True
            cur_depth += 1
            answer.append(dest)

            travle(port_dict, visit_dict, dest, answer,cur_depth, tot_depth)

            answer.pop()
            cur_depth -= 1
            visit_list[idx] = False


def solution(tickets):
    start_point = "ICN"
    answer = [start_point]
    cur_depth = 1
    tot_depth = len(tickets) + 1

    port_dict = {}
    visit_dict = {}

    for ticket in tickets:
        src = ticket[0]
        dest = ticket[1]

        # port_dict[src] = [dest] if src not in port_dict.keys() else port_dict[src].append(dest)

        if src in port_dict.keys():
            port_dict[src].append(dest)
            visit_dict[src].append(False)
        else:
            port_dict[src] = [dest]
            visit_dict[src] = [False]

    # 알파벳 순서대로 정렬
    for key, value in port_dict.items():
        value.sort()

    travle(port_dict, visit_dict, start_point, answer, cur_depth, tot_depth)

    return final_answer

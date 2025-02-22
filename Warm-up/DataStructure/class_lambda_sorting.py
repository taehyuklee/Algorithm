class Element:
    def __init__(self, x, y, z):
        self.x: int = x
        self.y: str = y
        self.z: long = z

    # def __str__(self):
    #     return "x: " + str(self.x) + " y: " + str(self.y) + " z: " + str(self.z)
    
    def __repr__(self):
        return "{x: " + str(self.x) + " y: " + str(self.y) + " z: " + str(self.z) + "}"

x_list = [13,2,3,5,5,6,7,7,9,10]
str_list = ["b","d","f","a","h","i","a","c","e","a"]
z_list = [3,1,2,5,1,3,2,3,1,4]

# for i in range(10):
#     listElement = ListElement(i, str_list[i], 10-i)

#     print(listElement)

listElement = []
for i in range(10):
    element: Element = Element(x_list[i], str_list[i], z_list[i])
    listElement.append(element)
    # print(element)


# print(listElement)
# sort에서 같은것이 있으면 그 다음 요소로 sort하는것들 
sorted_list = sorted(listElement, key=lambda element: -element.z)

# print(sorted_list)

sorted_list = sorted(listElement, key=lambda element: (-ord(element.y), element.x, element.z))

print(sorted_list)

'''
-ord(element.y): y 값은 내림차순으로 정렬됩니다. 문자열의 각 문자는 ord() 함수로 유니코드 값을 얻어서 비교합니다. 그리고 **-**를 붙여서 내림차순으로 만듭니다.

# 정렬 기준을 정의하는 함수 (lambda를 쓰지 않으면아래와 같이 함수를 만들어 줘야 한다)
def sort_key(element):
    return (element.x, -ord(element.y), element.z)
'''


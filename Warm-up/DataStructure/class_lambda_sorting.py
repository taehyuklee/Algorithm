class Element:
    def __init__(self, x, y, z):
        self.x: int = x
        self.y: str = y
        self.z: long = z

    # def __str__(self):
    #     return "x: " + str(self.x) + " y: " + str(self.y) + " z: " + str(self.z)
    
    def __repr__(self):
        return "x: " + str(self.x) + " y: " + str(self.y) + " z: " + str(self.z)

x_list = [1,2,3,5,5,6,7,7,9,10]
str_list = ["b","d","f","g","h","i","j","c","e","a"]
z_list = [3,1,2,5,1,3,2,3,1,4]

# for i in range(10):
#     listElement = ListElement(i, str_list[i], 10-i)

#     print(listElement)

listElement = []
for i in range(10):
    element: Element = Element(x_list[i], str_list[i], z_list[i])
    listElement.append(element)
    print(element)


print(listElement)
# sort에서 같은것이 있으면 그 다음 요소로 sort하는것들 
